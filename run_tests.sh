#!/bin/bash

rm -rf tests/allure-results tests/allure-report

pytest tests/test_login.py -v --alluredir=tests/allure-results

allure generate tests/allure-results -o tests/allure-report --clean

allure open tests/allure-report