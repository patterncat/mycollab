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
package com.esofthead.mycollab.module.crm.localization;

import ch.qos.cal10n.BaseName;
import ch.qos.cal10n.Locale;
import ch.qos.cal10n.LocaleData;

@BaseName("localization/crm/opportunity")
@LocaleData({ @Locale("en_US") })
public enum OpportunityI18nEnum {
	FORM_AMOUNT,
	FORM_SALE_STAGE,
	FORM_CLOSE_DATE,
	FORM_NAME,
	FORM_CURRENCY,
	FORM_PROBABILITY,
	FORM_ACCOUNT_NAME,
	FORM_TYPE,
	FORM_LEAD_SOURCE,
	FORM_CAMPAIGN_NAME,
	FORM_ASSIGN_USER
}
