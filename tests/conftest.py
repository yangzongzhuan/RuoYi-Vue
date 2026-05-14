import sys

from pathlib import Path

PROJECT_ROOT = Path(__file__).resolve().parents[1]

if str(PROJECT_ROOT) not in sys.path:

    sys.path.insert(0, str(PROJECT_ROOT))

import pytest

import requests

from utils.request_client import RequestClient

@pytest.fixture
def client(api, base_url):
    return RequestClient(api, base_url)

def pytest_addoption(parser):
    parser.addini(
        "base_url",
        help="RuoYi backend host, for example http://localhost:8080 or http://47.116.209.164",
        default="http://47.116.209.164",
    )
    parser.addoption(
        "--base-url",
        action="store",
        default=None,
        help="RuoYi backend host. This overrides RY_BASE_URL and pytest.ini base_url.",
    )


@pytest.fixture(scope="session")
def base_url(pytestconfig):
    import os

    value = (
        pytestconfig.getoption("--base-url")
        or os.getenv("RY_BASE_URL")
        or pytestconfig.getini("base_url")
    )
    return value.rstrip("/")


@pytest.fixture(scope="session")
def api(base_url):
    session = requests.Session()
    session.headers.update({"Content-Type": "application/json;charset=utf-8"})

    try:
        session.get(f"{base_url}/", timeout=5)
    except requests.RequestException as exc:
        pytest.skip(f"后端服务暂不可访问：{base_url} ({exc})")

    return session
