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
package com.esofthead.mycollab.module.project.view.message;

import com.esofthead.mycollab.common.UrlEncodeDecoder;
import com.esofthead.mycollab.core.arguments.SetSearchField;
import com.esofthead.mycollab.eventmanager.EventBus;
import com.esofthead.mycollab.module.project.domain.criteria.MessageSearchCriteria;
import com.esofthead.mycollab.module.project.events.ProjectEvent;
import com.esofthead.mycollab.module.project.view.ProjectUrlResolver;
import com.esofthead.mycollab.module.project.view.parameters.MessageScreenData;
import com.esofthead.mycollab.module.project.view.parameters.ProjectScreenData;
import com.esofthead.mycollab.vaadin.mvp.PageActionChain;

/**
 * 
 * @author MyCollab Ltd.
 * @since 1.0
 *
 */
public class MessageUrlResolver extends ProjectUrlResolver {
	public MessageUrlResolver() {
		this.addSubResolver("list", new ListUrlResolver());
		this.addSubResolver("preview", new PreviewUrlResolver());
	}

	private static class ListUrlResolver extends ProjectUrlResolver {
		@Override
		protected void handlePage(String... params) {
			String decodeUrl = UrlEncodeDecoder.decode(params[0]);
			int projectId = Integer.parseInt(decodeUrl);

			MessageSearchCriteria messageSearchCriteria = new MessageSearchCriteria();
			messageSearchCriteria.setProjectids(new SetSearchField<Integer>(
					projectId));

			PageActionChain chain = new PageActionChain(
					new ProjectScreenData.Goto(projectId),
					new MessageScreenData.Search(messageSearchCriteria));
			EventBus.getInstance().fireEvent(
					new ProjectEvent.GotoMyProject(this, chain));
		}
	}

	private static class PreviewUrlResolver extends ProjectUrlResolver {
		@Override
		protected void handlePage(String... params) {
			String decodeUrl = UrlEncodeDecoder.decode(params[0]);
			String[] tokens = decodeUrl.split("/");

			int projectId = Integer.parseInt(tokens[0]);
			int messageId = Integer.parseInt(tokens[1]);
			PageActionChain chain = new PageActionChain(
					new ProjectScreenData.Goto(projectId),
					new MessageScreenData.Read(messageId));
			EventBus.getInstance().fireEvent(
					new ProjectEvent.GotoMyProject(this, chain));
		}
	}
}
