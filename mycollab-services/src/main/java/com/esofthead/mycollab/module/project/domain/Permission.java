/**
 * This file is part of mycollab-services.
 *
 * mycollab-services is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * mycollab-services is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with mycollab-services.  If not, see <http://www.gnu.org/licenses/>.
 */
package com.esofthead.mycollab.module.project.domain;

import com.esofthead.mycollab.core.arguments.ValuedBean;

public class Permission extends ValuedBean {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column m_prj_permission.id
     *
     * @mbggenerated Thu Jan 17 14:57:49 GMT+07:00 2013
     */
    private Integer id;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column m_prj_permission.projectid
     *
     * @mbggenerated Thu Jan 17 14:57:49 GMT+07:00 2013
     */
    private Integer projectid;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column m_prj_permission.pathid
     *
     * @mbggenerated Thu Jan 17 14:57:49 GMT+07:00 2013
     */
    private String pathid;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column m_prj_permission.canAccess
     *
     * @mbggenerated Thu Jan 17 14:57:49 GMT+07:00 2013
     */
    private Boolean canaccess;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column m_prj_permission.username
     *
     * @mbggenerated Thu Jan 17 14:57:49 GMT+07:00 2013
     */
    private String username;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column m_prj_permission.id
     *
     * @return the value of m_prj_permission.id
     *
     * @mbggenerated Thu Jan 17 14:57:49 GMT+07:00 2013
     */
    public Integer getId() {
        return id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column m_prj_permission.id
     *
     * @param id the value for m_prj_permission.id
     *
     * @mbggenerated Thu Jan 17 14:57:49 GMT+07:00 2013
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column m_prj_permission.projectid
     *
     * @return the value of m_prj_permission.projectid
     *
     * @mbggenerated Thu Jan 17 14:57:49 GMT+07:00 2013
     */
    public Integer getProjectid() {
        return projectid;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column m_prj_permission.projectid
     *
     * @param projectid the value for m_prj_permission.projectid
     *
     * @mbggenerated Thu Jan 17 14:57:49 GMT+07:00 2013
     */
    public void setProjectid(Integer projectid) {
        this.projectid = projectid;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column m_prj_permission.pathid
     *
     * @return the value of m_prj_permission.pathid
     *
     * @mbggenerated Thu Jan 17 14:57:49 GMT+07:00 2013
     */
    public String getPathid() {
        return pathid;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column m_prj_permission.pathid
     *
     * @param pathid the value for m_prj_permission.pathid
     *
     * @mbggenerated Thu Jan 17 14:57:49 GMT+07:00 2013
     */
    public void setPathid(String pathid) {
        this.pathid = pathid;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column m_prj_permission.canAccess
     *
     * @return the value of m_prj_permission.canAccess
     *
     * @mbggenerated Thu Jan 17 14:57:49 GMT+07:00 2013
     */
    public Boolean getCanaccess() {
        return canaccess;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column m_prj_permission.canAccess
     *
     * @param canaccess the value for m_prj_permission.canAccess
     *
     * @mbggenerated Thu Jan 17 14:57:49 GMT+07:00 2013
     */
    public void setCanaccess(Boolean canaccess) {
        this.canaccess = canaccess;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column m_prj_permission.username
     *
     * @return the value of m_prj_permission.username
     *
     * @mbggenerated Thu Jan 17 14:57:49 GMT+07:00 2013
     */
    public String getUsername() {
        return username;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column m_prj_permission.username
     *
     * @param username the value for m_prj_permission.username
     *
     * @mbggenerated Thu Jan 17 14:57:49 GMT+07:00 2013
     */
    public void setUsername(String username) {
        this.username = username;
    }
}