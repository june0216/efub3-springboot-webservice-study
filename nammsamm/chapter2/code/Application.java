package awsStudy;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

//SpringBootApplication -> 스프링 부트의 자동 설정, 스프링 Bean 일기 생성 모두 자동으로 설정됨. 이 위치부터 설정 읽기 시작.
// 위의 이유로 이 클래스는 항상 최상단 위치.
@SpringBootApplication

//Application은 앞으로의 메인 클래스.
public class Application {
    public static void main(String[] args){
        SpringApplication.run(awsStudy.Application.class,args);
    }
}
