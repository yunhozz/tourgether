package com.tourgether.api;

import com.tourgether.domain.member.service.TeamService;
import com.tourgether.util.auth.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.tourgether.dto.TeamDto.*;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class TeamApiController {

    private TeamService teamService;

    @GetMapping("/teams/{teamId}")
    public ResponseEntity<TeamResponseDto> getTeam(@PathVariable String teamId) {
        return ResponseEntity.ok(teamService.findTeamDto(Long.valueOf(teamId)));
    }

    @GetMapping("/teams")
    public ResponseEntity<List<TeamResponseDto>> getTeamList() {
        return ResponseEntity.ok(teamService.findTeamDtoList());
    }

    @PostMapping("/teams")
    public ResponseEntity<Long> createTeam(@AuthenticationPrincipal UserDetailsImpl userDetails, @RequestBody TeamRequestDto teamRequestDto) {
        return ResponseEntity.ok(teamService.createTeam(teamRequestDto, userDetails.getMember().getId()));
    }

    @PatchMapping("/teams")
    public ResponseEntity<Void> updateTeam(@AuthenticationPrincipal UserDetailsImpl userDetails, @RequestParam String teamId, @RequestBody UpdateForm updateForm) {
        teamService.updateTeam(Long.valueOf(teamId), userDetails.getMember().getId(), updateForm);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/teams")
    public ResponseEntity<Void> deleteTeam(@AuthenticationPrincipal UserDetailsImpl userDetails, @RequestParam String teamId) {
        teamService.deleteTeam(Long.valueOf(teamId), userDetails.getMember().getId());
        return ResponseEntity.ok().build();
    }
}
