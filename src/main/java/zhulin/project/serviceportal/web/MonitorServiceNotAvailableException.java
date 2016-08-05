package zhulin.project.serviceportal.web;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value=HttpStatus.SERVICE_UNAVAILABLE,reason="Monitor Service Not Available")
public class MonitorServiceNotAvailableException extends RuntimeException {

}
