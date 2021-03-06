package com.esofthead.mycollab.common.dao;

import com.esofthead.mycollab.common.domain.SaveSearchResultExample;
import com.esofthead.mycollab.common.domain.SaveSearchResultWithBLOBs;
import com.esofthead.mycollab.core.persistence.ICrudGenericDAO;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface SaveSearchResultMapper extends ICrudGenericDAO {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table s_save_search_result
     *
     * @mbggenerated Sun Feb 09 16:15:22 ICT 2014
     */
    int countByExample(SaveSearchResultExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table s_save_search_result
     *
     * @mbggenerated Sun Feb 09 16:15:22 ICT 2014
     */
    int deleteByExample(SaveSearchResultExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table s_save_search_result
     *
     * @mbggenerated Sun Feb 09 16:15:22 ICT 2014
     */
    int deleteByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table s_save_search_result
     *
     * @mbggenerated Sun Feb 09 16:15:22 ICT 2014
     */
    int insert(SaveSearchResultWithBLOBs record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table s_save_search_result
     *
     * @mbggenerated Sun Feb 09 16:15:22 ICT 2014
     */
    int insertSelective(SaveSearchResultWithBLOBs record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table s_save_search_result
     *
     * @mbggenerated Sun Feb 09 16:15:22 ICT 2014
     */
    List<SaveSearchResultWithBLOBs> selectByExampleWithBLOBs(SaveSearchResultExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table s_save_search_result
     *
     * @mbggenerated Sun Feb 09 16:15:22 ICT 2014
     */
    SaveSearchResultWithBLOBs selectByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table s_save_search_result
     *
     * @mbggenerated Sun Feb 09 16:15:22 ICT 2014
     */
    int updateByExampleSelective(@Param("record") SaveSearchResultWithBLOBs record, @Param("example") SaveSearchResultExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table s_save_search_result
     *
     * @mbggenerated Sun Feb 09 16:15:22 ICT 2014
     */
    int updateByExampleWithBLOBs(@Param("record") SaveSearchResultWithBLOBs record, @Param("example") SaveSearchResultExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table s_save_search_result
     *
     * @mbggenerated Sun Feb 09 16:15:22 ICT 2014
     */
    int updateByPrimaryKeySelective(SaveSearchResultWithBLOBs record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table s_save_search_result
     *
     * @mbggenerated Sun Feb 09 16:15:22 ICT 2014
     */
    Integer insertAndReturnKey(SaveSearchResultWithBLOBs value);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table s_save_search_result
     *
     * @mbggenerated Sun Feb 09 16:15:22 ICT 2014
     */
    void removeKeysWithSession(List primaryKeys);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table s_save_search_result
     *
     * @mbggenerated Sun Feb 09 16:15:22 ICT 2014
     */
    void massUpdateWithSession(@Param("record") SaveSearchResultWithBLOBs record, @Param("primaryKeys") List primaryKeys);
}