/**
 * This file is part of mycollab-web.
 *
 * mycollab-web is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * mycollab-web is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with mycollab-web.  If not, see <http://www.gnu.org/licenses/>.
 */
package com.esofthead.mycollab.module.billing.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.esofthead.mycollab.common.localization.GenericI18Enum;
import com.esofthead.mycollab.configuration.PasswordEncryptHelper;
import com.esofthead.mycollab.core.MyCollabException;
import com.esofthead.mycollab.core.UserInvalidInputException;
import com.esofthead.mycollab.core.utils.LocalizationHelper;
import com.esofthead.mycollab.module.billing.RegisterStatusConstants;
import com.esofthead.mycollab.module.user.dao.UserMapper;
import com.esofthead.mycollab.module.user.domain.User;
import com.esofthead.mycollab.module.user.service.UserService;
import com.esofthead.mycollab.servlet.GenericServlet;
import com.esofthead.mycollab.spring.ApplicationContextUtil;
import com.esofthead.mycollab.utils.InvalidPasswordException;
import com.esofthead.mycollab.utils.PasswordCheckerUtil;

/**
 * 
 * @author MyCollab Ltd.
 * @since 1.0
 * 
 */
@Component("updateUserInfoServlet")
public class AnnotatedUserUpdateInfoHandlerServlet extends GenericServlet {
	private static Logger log = LoggerFactory
			.getLogger(AnnotatedUserUpdateInfoHandlerServlet.class);

	@Autowired
	private UserMapper userMapper;

	@Override
	protected void onHandleRequest(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		String errMsg = "";

		String username = request.getParameter("username");
		int sAccountId = Integer.parseInt(request.getParameter("accountId"));

		String password = request.getParameter("password");

		try {
			PasswordCheckerUtil.checkValidPassword(password);
		} catch (InvalidPasswordException e) {
			throw new UserInvalidInputException(e.getMessage());
		}

		User user = new User();
		user.setPassword(PasswordEncryptHelper.encryptSaltPassword(password));
		user.setUsername(username);

		try {
			log.debug("Update password of user {}", username);
			UserService userService = ApplicationContextUtil
					.getSpringBean(UserService.class);
			userService.updateWithSession(user, username);

			userService.updateUserAccountStatus(username, sAccountId,
					RegisterStatusConstants.ACTIVE);
		} catch (Exception e) {
			log.error("Error when update user - userAccount", e);
			errMsg = LocalizationHelper
					.getMessage(GenericI18Enum.ERROR_USER_NOTICE_INFORMATION_MESSAGE);
			throw new MyCollabException(errMsg);
		}
	}
}
