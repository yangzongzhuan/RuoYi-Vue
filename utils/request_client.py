import json
import allure


class RequestClient:
    def __init__(self, session, base_url):
        self.session = session
        self.base_url = base_url.rstrip("/")

    def post(self, path, json_data=None, **kwargs):
        url = f"{self.base_url}{path}"

        with allure.step(f"POST 请求：{path}"):
            allure.attach(
                url,
                name="请求地址",
                attachment_type=allure.attachment_type.TEXT
            )

            allure.attach(
                json.dumps(json_data, ensure_ascii=False, indent=2),
                name="请求参数",
                attachment_type=allure.attachment_type.JSON
            )

            response = self.session.post(
                url,
                json=json_data,
                timeout=kwargs.get("timeout", 10)
            )

            allure.attach(
                response.text,
                name="响应结果",
                attachment_type=allure.attachment_type.JSON
            )

            return response