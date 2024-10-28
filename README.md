## **커스텀 매크로 프로그램**

&nbsp;

## 📋 목차

- [📌 프로젝트 소개](#-프로젝트-소개)
- [📂 프로젝트 구조](#-프로젝트-구조)
- [🚀 기능](#-기능)
- [👀 UI 미리보기](#-ui-미리보기)

## 📌 **프로젝트 소개**

**반복적으로 필요한 기능들을 수행하는 프로그램입니다.**<br>
사용자가 지정한 반복 작업(예: 키 입력, 스크린샷 캡처 등)을 자동으로 수행하여 작업 효율을 높입니다.
&nbsp;

## 📂 **프로젝트 구조**

⚠️ 아래 구조는 지속적으로 수정/보완될 예정입니다!

```
└── src
    ├── main
    │   ├── java
    │   │   └── com
    │   │       └── custommacro
    │   │           └── custommacro
    │   │               ├── CustommacroApplication.java
    │   │               ├── Launcher.java
    │   │               ├── MainView.java
    │   │               ├── global
    │   │               │   ├── commonInterface
    │   │               │   │   └── MacroTask.java
    │   │               │   └── exception
    │   │               │       ├── CustomException.java
    │   │               │       └── ErrorMessage.java
    │   │               └── scheduledMacro
    │   │                   ├── controller
    │   │                   │   └── ScheduledMacroController.java
    │   │                   ├── domain
    │   │                   │   ├── CaptureMacro.java
    │   │                   │   ├── KeyMacro.java
    │   │                   │   └── ScheduledMacro.java
    │   │                   ├── service
    │   │                   │   ├── CaptureMacroService.java
    │   │                   │   ├── KeyMacroService.java
    │   │                   │   └── ScheduledMacroService.java
    │   │                   ├── util
    │   │                   └── view
    │   │                       ├── CaptureMacroView.java
    │   │                       └── ScheduledMacroView.java
    │   └── resources
    │       ├── application.properties
    │       └── logback.xml
    └── test
        └── java
            └── com
                └── custommacro
                    └── custommacro
                        ├── CustommacroApplicationTests.java
                        └── scheduledMacro
                            └── service
                                └── KeyMacroServiceTest.java


```

&nbsp;

## 🚀 **기능**

### 공통 기능

- 매크로를 시작하거나 중단할 수 있습니다.
- 반복 주기를 설정할 수 있습니다.
- 각 기능을 ON/OFF할 수 있습니다.

### 화면 캡쳐 기능

- **캡쳐 ON/OFF**: 체크박스를 통해 매크로 실행 시 캡처 기능을 실행할지 여부를 결정할 수 있습니다.
- **디스플레이 선택**: 여러 모니터 중 캡처할 디스플레이를 선택할 수 있습니다.
- **캡쳐 영역 선택**: 캡쳐를 원하는 영역을 마우스로 지정할 수 있습니다.
- **저장 경로 선택**: 캡처한 이미지를 저장할 경로를 지정할 수 있습니다.

### 반복 키 세팅

- **반복할 키 ON/OFF**: 체크박스를 통해 매크로 실행 시 반복 키 기능을 실행할지 여부를 결정할 수 있습니다.
- **반복할 키 입력**: 반복할 키를 지정할 수 있습니다.

&nbsp;

## 👀 **UI 미리보기**

⚠️ 아래 UI는 지속적으로 수정/보완될 예정입니다!
<div>
    <img src="https://github.com/user-attachments/assets/943af457-1f25-4281-bd18-f49209256515" alt="UI Preview" width="40%">
</div>

&nbsp;