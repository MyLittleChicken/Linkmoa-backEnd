package com.linkmoa.source.domain.page.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.linkmoa.source.auth.oauth2.principal.PrincipalDetails;
import com.linkmoa.source.domain.page.dto.request.PageCreateDto;
import com.linkmoa.source.domain.page.dto.request.PageDeleteDto;
import com.linkmoa.source.domain.page.dto.request.SharePageDashboardDto;
import com.linkmoa.source.domain.page.dto.request.SharePageDashboardPermissionUpdateDto;
import com.linkmoa.source.domain.page.dto.response.PageDetailsResponse;
import com.linkmoa.source.domain.page.dto.response.PageResponse;
import com.linkmoa.source.domain.page.dto.response.SharePageLeaveResponse;
import com.linkmoa.source.domain.page.entity.Page;
import com.linkmoa.source.domain.page.resolver.PageParameterResolver;
import com.linkmoa.source.domain.page.service.PageService;
import com.linkmoa.source.global.constant.CommandType;
import com.linkmoa.source.global.dto.request.BaseRequest;
import com.linkmoa.source.global.spec.ApiResponseSpec;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/page")
public class PageApiController {

	private final PageService pageService;
	private final PageParameterResolver pageParameterResolver;

	@PostMapping
	@PreAuthorize("isAuthenticated()")
	public ResponseEntity<ApiResponseSpec<Long>> createPage(
		@RequestBody @Validated PageCreateDto.Request pageCreateDto,
		@AuthenticationPrincipal PrincipalDetails principalDetails) {
		Page sharedPage = pageService.createSharedPage(pageCreateDto,
			principalDetails);

		return ResponseEntity.ok().body(ApiResponseSpec.success(
			HttpStatus.OK,
			sharedPage.getPageType().toString() + "페이지 생성에 성공했습니다.",
			sharedPage.getId()
		));
	}

	@DeleteMapping
	@PreAuthorize("isAuthenticated()")
	public ResponseEntity<ApiResponseSpec<Long>> deletePage(
		@RequestBody @Validated PageDeleteDto.Request pageDeleteDto,
		@AuthenticationPrincipal PrincipalDetails principalDetails) {

		return ResponseEntity.ok().body(ApiResponseSpec.success(
			HttpStatus.OK,
			"페이지 삭제에 성공했습니다.",
			pageService.deletePage(pageDeleteDto, principalDetails)
		));
	}

	@GetMapping()
	@PreAuthorize("isAuthenticated()")
	public ResponseEntity<ApiResponseSpec<List<PageResponse>>> getAllPages(
		@AuthenticationPrincipal PrincipalDetails principalDetails) {

		return ResponseEntity.ok().body(ApiResponseSpec.success(
			HttpStatus.OK,
			"현재 회원이 참여 중인 모든 페이지를 조회했습니다.",
			pageService.findAllPages(principalDetails)
		));

	}

	@DeleteMapping("/leave")
	@PreAuthorize("isAuthenticated()")
	public ResponseEntity<ApiResponseSpec<SharePageLeaveResponse>> leaveSharePage(
		@RequestBody @Validated BaseRequest baseRequest,
		@AuthenticationPrincipal PrincipalDetails principalDetails) {

		return ResponseEntity.ok().body(ApiResponseSpec.success(
			HttpStatus.OK,
			"공유 페이지 탈퇴에 성공했습니다.",
			pageService.leaveSharePage(baseRequest,
				principalDetails)
		));
	}

	@GetMapping("/details")
	@PreAuthorize("isAuthenticated()")
	public ResponseEntity<ApiResponseSpec<PageDetailsResponse>> getPageDetails(
		@RequestParam("pageId") Long pageId,
		@RequestParam("commandType") CommandType commandType,
		@AuthenticationPrincipal PrincipalDetails principalDetails) {

		return ResponseEntity.ok().body(ApiResponseSpec.success(
			HttpStatus.OK,
			"페이지 접속 시, 해당 페이지 메인화면을 조회합니다",
			pageService.getPageMain(pageParameterResolver.createBaseRequestFrom(pageId, commandType), principalDetails)
		));
	}

	@GetMapping("/login")
	@PreAuthorize("isAuthenticated()")
	public ResponseEntity<ApiResponseSpec<PageDetailsResponse>> loadPersonalPageMain(
		@AuthenticationPrincipal PrincipalDetails principalDetails) {
		return ResponseEntity.ok().body(ApiResponseSpec.success(
			HttpStatus.OK,
			"로그인 성공 시, 유저의 개인 페이지 메인 화면 데이터를 조회합니다.",
			pageService.loadPersonalPageMain(principalDetails)
		));
	}

	@GetMapping("/dashboard")
	@PreAuthorize("isAuthenticated()")
	public ResponseEntity<ApiResponseSpec<SharePageDashboardDto.Response>> getPageDashboard(
		@RequestBody SharePageDashboardDto.Request request,
		@AuthenticationPrincipal PrincipalDetails principalDetails
	) {
		return ResponseEntity.ok().body(ApiResponseSpec.success(
			HttpStatus.OK,
			"공유 페이지 대시보드 정보를 조회합니다.",
			pageService.getPageDashboard(request)

		));
	}

	@PutMapping("/dashboard")
	@PreAuthorize("isAuthenticated()")
	public ResponseEntity<ApiResponseSpec<SharePageDashboardPermissionUpdateDto.Response>> updateSharePagePermission(
		@RequestBody SharePageDashboardPermissionUpdateDto.Request request,
		@AuthenticationPrincipal PrincipalDetails principalDetails
	) {
		return ResponseEntity.ok().body(ApiResponseSpec.success(
			HttpStatus.OK,
			"공유 페이지 멤버 권한을 수정했습니다.",
			pageService.updateSharePagePermission(request, principalDetails)
		));
	}
}
