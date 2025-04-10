# 링크모아에 오신 것을 환영합니다!

## 실행 가이드
- 해당 프로젝트는 `Java 17`로 작성되었습니다.
- `Docker`를 사용하여 실행할 수 있습니다.
- .env.example 파일을 복사하여 `.env.{env}` 파일을 생성 후 값을 작성합니다. (예: `.env.dev`, `.env.local`)

1. 로컬 환경에서
- 어플리케이션을 빌드하기 위해 아래 명령어를 입력합니다.
```shell
 ./gradlew clean build
```
- 실행을 위해 아래 명령어를 입력합니다.
```shell
 docker compose -f docker-compose.yml -f docker-compose.local.yml --env-file .env.local up -d --build
```

2. 개발 환경에서
> 개발 환경은 별도의 rdb 서버를 사용합니다.
- 어플리케이션을 빌드하기 위해 아래 명령어를 입력합니다.
```shell
 ./gradlew clean build
```
- 실행을 위해 아래 명령어를 입력합니다.
```shell
 docker compose --env-file .env.dev up -d --build
```