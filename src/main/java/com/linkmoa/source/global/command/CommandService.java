package com.linkmoa.source.global.command;

import java.util.EnumMap;
import java.util.Map;
import java.util.Set;

import org.springframework.stereotype.Service;

import com.linkmoa.source.domain.memberPageLink.constant.PermissionType;
import com.linkmoa.source.domain.memberPageLink.repository.MemberPageLinkDataAccess;
import com.linkmoa.source.global.constant.CommandType;

@Service
public class CommandService {

	private final Map<PermissionType, Set<CommandType>> permissionToCommandsMap = new EnumMap<>(PermissionType.class);
	private final MemberPageLinkDataAccess memberPageLinkRepository;

	public CommandService(MemberPageLinkDataAccess memberPageLinkDataAccess) {

		this.memberPageLinkRepository = memberPageLinkDataAccess;

		permissionToCommandsMap.put(PermissionType.ADMIN, Set.of(
			CommandType.VIEW, CommandType.EDIT, CommandType.CREATE, CommandType.SHARED_PAGE_LEAVE,
			CommandType.DIRECTORY_TRANSMISSION, CommandType.SHARED_PAGE_INVITATION,
			CommandType.SHARED_PAGE_DELETION, CommandType.SHARED_PAGE_USER_REMOVAL,
			CommandType.SHARED_PAGE_PERMISSION_CHANGE
		));

		permissionToCommandsMap.put(PermissionType.HOST, Set.of(
			CommandType.VIEW, CommandType.EDIT, CommandType.CREATE,
			CommandType.DIRECTORY_TRANSMISSION, CommandType.SHARED_PAGE_INVITATION,
			CommandType.SHARED_PAGE_DELETION, CommandType.SHARED_PAGE_USER_REMOVAL,
			CommandType.SHARED_PAGE_PERMISSION_CHANGE
		));

		permissionToCommandsMap.put(PermissionType.EDITOR, Set.of(
			CommandType.CREATE, CommandType.VIEW, CommandType.EDIT, CommandType.DIRECTORY_TRANSMISSION,
			CommandType.SHARED_PAGE_LEAVE
		));

		permissionToCommandsMap.put(PermissionType.VIEWER, Set.of(
			CommandType.VIEW, CommandType.SHARED_PAGE_LEAVE
		));
	}

	public boolean canExecute(PermissionType permissionType, CommandType commandType) {
		return permissionToCommandsMap.getOrDefault(permissionType, Set.of()).contains(commandType);
	}

	public PermissionType getUserPermissionType(Long memberId, Long pageId) {
		return memberPageLinkRepository.findPermissionTypeByMemberIdAndPageId(memberId, pageId);

	}

}
