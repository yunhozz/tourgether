package com.tourgether.domain.chat.model.repository;

import com.tourgether.domain.chat.model.dto.response.ChatRoomResponseDto;

import java.util.List;

public interface ChatRoomRepositoryCustom {

    List<ChatRoomResponseDto> findWithUserId(Long userId);
}
