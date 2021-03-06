package com.esofthead.mycollab.module.project.dao;

import com.esofthead.mycollab.core.persistence.ICrudGenericDAO;
import com.esofthead.mycollab.module.project.domain.TaskList;
import com.esofthead.mycollab.module.project.domain.TaskListExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface TaskListMapper extends ICrudGenericDAO {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table m_prj_task_list
     *
     * @mbggenerated Sun Feb 09 16:15:22 ICT 2014
     */
    int countByExample(TaskListExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table m_prj_task_list
     *
     * @mbggenerated Sun Feb 09 16:15:22 ICT 2014
     */
    int deleteByExample(TaskListExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table m_prj_task_list
     *
     * @mbggenerated Sun Feb 09 16:15:22 ICT 2014
     */
    int deleteByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table m_prj_task_list
     *
     * @mbggenerated Sun Feb 09 16:15:22 ICT 2014
     */
    int insert(TaskList record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table m_prj_task_list
     *
     * @mbggenerated Sun Feb 09 16:15:22 ICT 2014
     */
    int insertSelective(TaskList record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table m_prj_task_list
     *
     * @mbggenerated Sun Feb 09 16:15:22 ICT 2014
     */
    List<TaskList> selectByExampleWithBLOBs(TaskListExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table m_prj_task_list
     *
     * @mbggenerated Sun Feb 09 16:15:22 ICT 2014
     */
    List<TaskList> selectByExample(TaskListExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table m_prj_task_list
     *
     * @mbggenerated Sun Feb 09 16:15:22 ICT 2014
     */
    TaskList selectByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table m_prj_task_list
     *
     * @mbggenerated Sun Feb 09 16:15:22 ICT 2014
     */
    int updateByExampleSelective(@Param("record") TaskList record, @Param("example") TaskListExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table m_prj_task_list
     *
     * @mbggenerated Sun Feb 09 16:15:22 ICT 2014
     */
    int updateByExampleWithBLOBs(@Param("record") TaskList record, @Param("example") TaskListExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table m_prj_task_list
     *
     * @mbggenerated Sun Feb 09 16:15:22 ICT 2014
     */
    int updateByExample(@Param("record") TaskList record, @Param("example") TaskListExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table m_prj_task_list
     *
     * @mbggenerated Sun Feb 09 16:15:22 ICT 2014
     */
    int updateByPrimaryKeySelective(TaskList record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table m_prj_task_list
     *
     * @mbggenerated Sun Feb 09 16:15:22 ICT 2014
     */
    Integer insertAndReturnKey(TaskList value);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table m_prj_task_list
     *
     * @mbggenerated Sun Feb 09 16:15:22 ICT 2014
     */
    void removeKeysWithSession(List primaryKeys);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table m_prj_task_list
     *
     * @mbggenerated Sun Feb 09 16:15:22 ICT 2014
     */
    void massUpdateWithSession(@Param("record") TaskList record, @Param("primaryKeys") List primaryKeys);
}