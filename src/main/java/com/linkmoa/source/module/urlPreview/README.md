# URL Preview Module

URL 미리보기 기능을 제공하는 모듈입니다. 다양한 플랫폼(YouTube, OpenGraph 등)의 URL에서 메타데이터를 추출하여 미리보기 정보를 제공합니다.

## 📁 패키지 구조

```
src/main/java/com/qrorder/web/api/urlpreview/
├── UrlPreviewService.java          # 진입점 서비스
├── UrlPreviewContext.java          # 전략 컨텍스트 (Strategy Pattern)
├── strategy/                       # 전략 패턴 구현
│   ├── UrlPreviewStrategy.java     # 전략 인터페이스
│   ├── opengraph/                  # OpenGraph 전략 모듈
│   │   ├── OpenGraphStrategy.java  # OpenGraph 메타데이터 추출
│   │   └── OpenGraphConstants.java # OpenGraph 관련 상수
│   └── youtube/                    # YouTube 전략 모듈
│       ├── YoutubeStrategy.java    # YouTube 메타데이터 추출
│       └── YoutubeConstants.java   # YouTube 관련 상수
├── dto/                           # 데이터 전송 객체
│   ├── UrlPreviewRequest.java     # 요청 DTO
│   └── UrlPreviewResponse.java    # 응답 DTO
└── common/                        # 공통 요소
    └── ErrorMessages.java         # 공통 오류 메시지
```

## 🏗️ 아키텍처 설계

### Strategy Pattern 적용
- **UrlPreviewStrategy**: 전략 인터페이스
- **OpenGraphStrategy**: 일반적인 웹사이트 메타데이터 추출 (fallback)
- **YoutubeStrategy**: YouTube URL 전용 메타데이터 추출
- **UrlPreviewContext**: 전략 선택 및 실행

### 모듈화 설계
각 전략은 독립적인 모듈로 구성되어 있어 새로운 전략 추가 시 기존 코드 변경 없이 확장 가능합니다.

## 🚀 사용 방법

### 1. 서비스 주입 및 사용

```java
@Service
@RequiredArgsConstructor  
public class ExampleService {
    private final UrlPreviewService urlPreviewService;
    
    public void getUrlPreview() {
        UrlPreviewRequest request = UrlPreviewRequest.builder()
            .url("https://www.youtube.com/watch?v=dQw4w9WgXcQ")
            .build();
            
        UrlPreviewResponse response = urlPreviewService.getPreview(request);
        
        System.out.println("Title: " + response.getTitle());
        System.out.println("Description: " + response.getDescription());
        System.out.println("Image: " + response.getImage());
        System.out.println("URL: " + response.getUrl());
    }
}
```

## 🔧 전략별 동작 방식

### YouTube Strategy
- **지원 URL 패턴**: `youtube.com/watch?v=`, `youtu.be/`
- **추출 정보**: 제목, 썸네일 이미지
- **우선순위**: 높음 (YouTube URL일 경우 우선 선택)

### OpenGraph Strategy  
- **지원 URL**: 모든 URL (fallback 전략)
- **추출 정보**: og:title, og:description, og:image, og:url
- **우선순위**: 낮음 (다른 전략이 지원하지 않을 때 사용)

## 📦 새로운 전략 추가하기

새로운 플랫폼(예: Twitter) 지원을 위한 전략 추가 방법:

### 1. 패키지 생성
```
strategy/twitter/
├── TwitterStrategy.java
└── TwitterConstants.java
```

### 2. 상수 정의
```java
// TwitterConstants.java
public final class TwitterConstants {
    public static final String URL_PATTERN = "^https?://(?:www\\.)?twitter\\.com/.+";
    public static final String API_URL = "https://api.twitter.com/...";
    
    @Getter
    public enum ErrorMessages {
        FETCH_FAILED("Failed to fetch Twitter"),
        INVALID_URL("Invalid Twitter URL");
        
        private final String message;
        ErrorMessages(String message) { this.message = message; }
    }
}
```

### 3. 전략 구현
```java
// TwitterStrategy.java
@Component
public class TwitterStrategy implements UrlPreviewStrategy {
    
    @Override
    public boolean supports(String url) {
        return url.matches(TwitterConstants.URL_PATTERN);
    }
    
    @Override
    public UrlPreviewResponse extract(String url) {
        // Twitter API 호출 로직 구현
        return UrlPreviewResponse.builder()
            .title("...")
            .description("...")
            .image("...")
            .url(url)
            .build();
    }
}
```

### 4. 자동 등록
Spring의 `@Component`에 의해 자동으로 전략 목록에 등록되어 사용 가능합니다.

## 🛡️ 오류 처리

- **네트워크 오류**: 각 전략별로 정의된 오류 메시지 반환
- **지원하지 않는 URL**: `NO_STRATEGY_FOUND` 오류 발생
- **타임아웃**: 3초 타임아웃 설정 (Jsoup 기본값)

## 🔍 주요 특징

- **확장성**: Strategy Pattern으로 새로운 플랫폼 지원 용이
- **모듈화**: 각 전략이 독립적으로 관리됨
- **응집도**: 관련 기능들이 동일 패키지에 위치
- **유지보수성**: 전략별 수정 시 해당 모듈만 변경
- **타입 안전성**: Lombok과 Builder Pattern 활용

## 📋 의존성

- **Spring Boot**: 의존성 주입 및 컴포넌트 관리
- **Jsoup**: HTML 파싱 및 메타데이터 추출  
- **Lombok**: 보일러플레이트 코드 제거
- **Java 8+**: Stream API 및 람다 표현식 활용