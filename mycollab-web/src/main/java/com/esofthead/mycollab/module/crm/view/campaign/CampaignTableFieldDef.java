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
package com.esofthead.mycollab.module.crm.view.campaign;

import com.esofthead.mycollab.common.localization.GenericI18Enum;
import com.esofthead.mycollab.core.utils.LocalizationHelper;
import com.esofthead.mycollab.module.crm.localization.CampaignI18nEnum;
import com.esofthead.mycollab.module.crm.localization.CrmCommonI18nEnum;
import com.esofthead.mycollab.vaadin.ui.UIConstants;
import com.esofthead.mycollab.vaadin.ui.table.TableViewField;

/**
 * 
 * @author MyCollab Ltd.
 * @since 1.0
 * 
 */
public interface CampaignTableFieldDef {
	public static TableViewField selected = new TableViewField("", "selected",
			UIConstants.TABLE_CONTROL_WIDTH);

	public static TableViewField action = new TableViewField("", "id",
			UIConstants.TABLE_ACTION_CONTROL_WIDTH);

	public static TableViewField actualcost = new TableViewField(
			LocalizationHelper.getMessage(CampaignI18nEnum.FORM_ACTUAL_COST),
			"actualcost", UIConstants.TABLE_M_LABEL_WIDTH);

	public static TableViewField budget = new TableViewField(
			LocalizationHelper.getMessage(CampaignI18nEnum.FORM_BUDGET),
			"budget", UIConstants.TABLE_M_LABEL_WIDTH);

	public static TableViewField campaignname = new TableViewField(
			LocalizationHelper.getMessage(CrmCommonI18nEnum.TABLE_NAME_HEADER),
			"campaignname", UIConstants.TABLE_X_LABEL_WIDTH);

	public static TableViewField status = new TableViewField(
			LocalizationHelper.getMessage(CampaignI18nEnum.FORM_STATUS),
			"status", UIConstants.TABLE_M_LABEL_WIDTH);

	public static TableViewField type = new TableViewField(
			LocalizationHelper.getMessage(CampaignI18nEnum.FORM_TYPE), "type",
			UIConstants.TABLE_S_LABEL_WIDTH);

	public static TableViewField expectedCost = new TableViewField(
			LocalizationHelper.getMessage(CampaignI18nEnum.FORM_EXPECTED_COST),
			"expectedcost", UIConstants.TABLE_M_LABEL_WIDTH);

	public static TableViewField expectedRevenue = new TableViewField(
			LocalizationHelper
					.getMessage(CampaignI18nEnum.FORM_EXPECTED_REVENUE),
			"expectedrevenue", UIConstants.TABLE_M_LABEL_WIDTH);

	public static TableViewField endDate = new TableViewField(
			LocalizationHelper.getMessage(CampaignI18nEnum.FORM_END_DATE),
			"enddate", UIConstants.TABLE_DATE_WIDTH);

	public static TableViewField startDate = new TableViewField(
			LocalizationHelper.getMessage(CampaignI18nEnum.FORM_START_DATE),
			"startdate", UIConstants.TABLE_DATE_WIDTH);

	public static TableViewField assignUser = new TableViewField(
			LocalizationHelper.getMessage(GenericI18Enum.FORM_ASSIGNEE_FIELD),
			"assignUserFullName", UIConstants.TABLE_X_LABEL_WIDTH);

}
