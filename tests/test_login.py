from pathlib import Path

import pytest
import allure
import json

from utils.request_client import RequestClient

LOGIN_PATH = "/prod-api/login"
CASES_FILE = Path(__file__).resolve().parents[1] / "data" / "login_cases.yaml"

def load_login_cases():
    try:
        import yaml
    except ModuleNotFoundError:
        return load_simple_yaml_cases(CASES_FILE)

    with CASES_FILE.open(encoding="utf-8") as file:
        return yaml.safe_load(file)


def load_simple_yaml_cases(path):
    cases = []
    current = None

    with path.open(encoding="utf-8") as file:
        for raw_line in file:
            line = raw_line.strip()
            if not line or line.startswith("#"):
                continue
            if line.startswith("- "):
                if current:
                    cases.append(current)
                current = {}
                line = line[2:]
            if ":" not in line:
                continue
            key, value = line.split(":", 1)
            value = value.strip().strip('"').strip("'")
            if value.lower() == "true":
                value = True
            elif value.lower() == "false":
                value = False
            if current is None:
                current = {}
            current[key.strip()] = value

    if current:
        cases.append(current)
    return cases


def case_id(case):
    return case["case_name"]


def login(client, username, password):
    payload = {
        "username": username,
        "password": password,
        "code": "",
        "uuid": "",
    }

    return client.post(LOGIN_PATH, json_data=payload)

@allure.feature("登录模块")
@pytest.mark.parametrize("case", load_login_cases(), ids=case_id)
def test_login(case, client):

    allure.dynamic.title(case["case_name"])

    with allure.step("发送登录请求"):
        response = login(client, case["username"], case["password"])

    with allure.step("校验HTTP状态码"):
        assert response.status_code == 200

    with allure.step("解析响应数据"):
        body = response.json()

    with allure.step("校验业务响应"):
        if case["success"]:
            assert body["code"] == 200
            assert body.get("token")
        else:
            assert body["code"] != 200
            assert "token" not in body
