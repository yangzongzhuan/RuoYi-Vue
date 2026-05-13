import requests


BASE_URL = "http://47.116.209.164/prod-api"


def test_login():
    # 1. 获取验证码
    captcha_url = f"{BASE_URL}/captchaImage"
    captcha_response = requests.get(captcha_url)
    captcha_json = captcha_response.json()

    print(captcha_json)

    assert captcha_response.status_code == 200
    assert captcha_json["code"] == 200

    uuid = captcha_json["uuid"]

    # 2. 使用验证码登录
    login_url = f"{BASE_URL}/login"

    data = {
        "username": "admin",
        "password": "admin123",
        "code": "1234",
        "uuid": uuid
    }

    response = requests.post(login_url, json=data)

    print(response.json())

    assert response.status_code == 200
    assert response.json()["code"] == 200