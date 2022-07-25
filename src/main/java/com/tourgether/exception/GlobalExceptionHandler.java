package com.tourgether.exception;

import com.tourgether.dto.ErrorResponseDto;
import com.tourgether.enums.ErrorCode;
import com.tourgether.exception.chat.ChatNotFoundException;
import com.tourgether.exception.member.EmailDuplicateException;
import com.tourgether.exception.member.MemberNotFoundException;
import com.tourgether.exception.member.NicknameDuplicationException;
import com.tourgether.exception.member.PasswordMismatchException;
import com.tourgether.exception.notification.NotificationNotFoundException;
import com.tourgether.exception.recruit.RecruitNotFoundException;
import com.tourgether.exception.recruit.WriterMismatchException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {

    // exception
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponseDto> handleException(Exception e) {
        log.error("handleException", e);
        ErrorResponseDto error = new ErrorResponseDto(ErrorCode.INTER_SERVER_ERROR);

        return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    // runtime exception
    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ErrorResponseDto> handleRuntimeException(RuntimeException e) {
        log.error("handleRuntimeException", e);
        ErrorResponseDto error = new ErrorResponseDto(ErrorCode.INTER_SERVER_ERROR);

        return new ResponseEntity<>(error, HttpStatus.REQUEST_TIMEOUT);
    }

    // 회원 조회 실패
    @ExceptionHandler(MemberNotFoundException.class)
    public ResponseEntity<ErrorResponseDto> handleMemberNotFoundException(MemberNotFoundException e) {
        log.error("handleMemberNotFoundException", e);
        ErrorResponseDto error = new ErrorResponseDto(e.getErrorCode());

        return new ResponseEntity<>(error, HttpStatus.valueOf(e.getErrorCode().getStatus()));
    }

    // 이메일 중복
    @ExceptionHandler(EmailDuplicateException.class)
    public ResponseEntity<ErrorResponseDto> handleEmailDuplicateException(EmailDuplicateException e) {
        log.error("handleEmailDuplicateException", e);
        ErrorResponseDto error = new ErrorResponseDto(e.getErrorCode());

        return new ResponseEntity<>(error, HttpStatus.valueOf(e.getErrorCode().getStatus()));
    }

    // 닉네임 중복
    @ExceptionHandler(NicknameDuplicationException.class)
    public ResponseEntity<ErrorResponseDto> handleNicknameDuplicationException(NicknameDuplicationException e) {
        log.error("handleNicknameDuplicationException", e);
        ErrorResponseDto error = new ErrorResponseDto(e.getErrorCode());

        return new ResponseEntity<>(error, HttpStatus.valueOf(e.getErrorCode().getStatus()));
    }

    // 비밀번호 불일치
    @ExceptionHandler(PasswordMismatchException.class)
    public ResponseEntity<ErrorResponseDto> handlePasswordMismatchException(PasswordMismatchException e) {
        log.error("handlePasswordMismatchException", e);
        ErrorResponseDto error = new ErrorResponseDto(e.getErrorCode());

        return new ResponseEntity<>(error, HttpStatus.valueOf(e.getErrorCode().getStatus()));
    }

    // 알림 조회 실패
    @ExceptionHandler(NotificationNotFoundException.class)
    public ResponseEntity<ErrorResponseDto> handleNotificationNotFoundException(NotificationNotFoundException e) {
        log.error("handleNotificationNotFoundException", e);
        ErrorResponseDto error = new ErrorResponseDto(e.getErrorCode());

        return new ResponseEntity<>(error, HttpStatus.valueOf(e.getErrorCode().getStatus()));
    }

    // 모집글 조회 실패
    @ExceptionHandler(RecruitNotFoundException.class)
    public ResponseEntity<ErrorResponseDto> handleRecruitNotFoundException(RecruitNotFoundException e) {
        log.error("handleRecruitNotFoundException", e);
        ErrorResponseDto error = new ErrorResponseDto(e.getErrorCode());

        return new ResponseEntity<>(error, HttpStatus.valueOf(e.getErrorCode().getStatus()));
    }

    // 모집글 작성자 불일치
    @ExceptionHandler(WriterMismatchException.class)
    public ResponseEntity<ErrorResponseDto> handleWriterMismatchException(WriterMismatchException e) {
        log.error("handleWriterMismatchException", e);
        ErrorResponseDto error = new ErrorResponseDto(e.getErrorCode());

        return new ResponseEntity<>(error, HttpStatus.valueOf(e.getErrorCode().getStatus()));
    }

    // 채팅 조회 실패
    @ExceptionHandler(ChatNotFoundException.class)
    public ResponseEntity<ErrorResponseDto> handlerChatNotFoundException(ChatNotFoundException e) {
        log.error("handlerChatNotFoundException", e);
        ErrorResponseDto error = new ErrorResponseDto(e.getErrorCode());

        return new ResponseEntity<>(error, HttpStatus.valueOf(e.getErrorCode().getStatus()));
    }
}
