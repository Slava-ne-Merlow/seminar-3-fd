"""Readable console results and a preformatted GitHub Actions summary."""
import os
import re
import sys
from pathlib import Path
from xml.etree import ElementTree as ET

TITLES = {
    "homework": "Домашняя работа · Открытые тесты",
    "task01": "01 · Два дополнительных разряда",
    "task02": "02 · Биты чётности",
    "task03": "03 · Проездной",
    "task04": "04 · Доставка",
    "task05": "05 · Групповая поездка",
}


def render(module, directory, test_exit_code="0"):
    files = sorted(directory.glob("TEST-*.xml"))
    lines = [TITLES[module], ""]
    if not files:
        if module in {"task04", "task05", "homework"}:
            lines += ["В этой задаче классы нужно написать с нуля. Пока типы из условия не созданы, тесты не компилируются.", ""]
        return "\n".join(lines + [
            "Тесты не запустились.",
            "Откройте шаг «Проверить решение»: там указана причина.",
            "Возможна ошибка компиляции, загрузки зависимостей или настройки Java.",
        ]) + "\n", False

    try:
        cases = [case for file in files for case in ET.parse(file).getroot().iter("testcase")]
    except (ET.ParseError, OSError):
        return "\n".join(lines + ["Не удалось прочитать отчёт. Откройте шаг «Проверить решение»."]) + "\n", False
    results = []
    for case in cases:
        failure = case.find("failure")
        if failure is None:
            failure = case.find("error")
        state = "failed" if failure is not None else ("skipped" if case.find("skipped") is not None else "passed")
        results.append((case, failure, state))
    passed = sum(state == "passed" for _, _, state in results)
    failed = sum(state == "failed" for _, _, state in results)
    skipped = sum(state == "skipped" for _, _, state in results)
    lines += [f"Пройдено: {passed} из {len(cases)} · Не пройдено: {failed} · Пропущено: {skipped}", ""]
    has_todo = False
    for case, failure, state in results:
        name = case.get("name", "Проверка").removesuffix("()")
        icon = {"failed": "❌", "skipped": "⏭", "passed": "✅"}[state]
        lines.append(f"{icon} {name}")
        if failure is not None:
            message = (failure.get("message") or (failure.text or "").split("\n")[0]).strip()
            if "NotImplementedError" in message:
                has_todo = True
                detail = message.partition("An operation is not implemented:")[2].strip()
                message = "В решении остался TODO." + (f" {detail}" if detail else "")
            else:
                message = re.sub(r"^(?:org\.opentest4j\.AssertionFailedError|java\.lang\.AssertionError):\s*", "", message)
            for line in (message or "Подробности — в шаге «Проверить решение».")[:1500].splitlines():
                lines.append(f"   {line}")
        lines.append("")
    if not cases:
        lines.append("В отчёте нет проверок. Откройте шаг «Проверить решение».")
    elif has_todo:
        lines.append("Замените TODO своим кодом и отправьте новый commit и push.")
    elif passed == len(cases) and test_exit_code == "0":
        lines.append("Все проверки задачи пройдены!")
    if test_exit_code != "0":
        lines += ["", "Запуск завершился с ошибкой или не был завершён. Подробности — в шаге «Проверить решение»."]
    success = bool(cases) and passed == len(cases) and test_exit_code == "0"
    return "\n".join(lines).rstrip() + "\n", success


def main():
    if len(sys.argv) != 2 or sys.argv[1] not in TITLES:
        raise SystemExit("Укажите задачу: task01 … task05 или homework")
    module = sys.argv[1]
    text, success = render(module, Path(module, "build/test-results/test"), os.environ.get("TEST_EXIT_CODE", "0"))
    print(text, end="")
    if os.environ.get("GITHUB_STEP_SUMMARY"):
        # Keep exception text literal, including <, >, Markdown, and backticks.
        longest = max((len(match) for match in re.findall(r"`+", text)), default=0)
        fence = "`" * max(3, longest + 1)
        with open(os.environ["GITHUB_STEP_SUMMARY"], "a", encoding="utf-8") as output:
            output.write(f"{fence}text\n{text}{fence}\n\n")

    return 0 if success else 1


if __name__ == "__main__":
    raise SystemExit(main())
