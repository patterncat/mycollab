package com.esofthead.mycollab.core.persistence;

import java.io.IOException;
import java.io.InputStream;

import org.apache.ibatis.executor.parameter.ParameterHandler;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.SqlSource;
import org.apache.ibatis.parsing.XNode;
import org.apache.ibatis.scripting.LanguageDriver;
import org.apache.ibatis.scripting.defaults.DefaultParameterHandler;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.type.Alias;
import org.mybatis.scripting.velocity.SQLScriptSource;

import com.esofthead.mycollab.core.MyCollabException;

/**
 * 
 * @author MyCollab Ltd.
 * @since 4.0
 * 
 */
@Alias("velocity")
public class VelocityDriverDeclare implements LanguageDriver {
	private static final String TOTAL_COUNT_EXPR_MACRO;
	private static final String SELECT_EXPR_MACRO;

	static {
		TOTAL_COUNT_EXPR_MACRO = loadResource("totalCountExpr");
		SELECT_EXPR_MACRO = loadResource("selectExpr");
	}

	@Override
	public ParameterHandler createParameterHandler(
			MappedStatement mappedStatement, Object parameterObject,
			BoundSql boundSql) {
		return new DefaultParameterHandler(mappedStatement, parameterObject,
				boundSql);
	}

	@Override
	public SqlSource createSqlSource(Configuration configuration, XNode script,
			Class<?> parameterTypeClass) {
		if (parameterTypeClass == null) {
			parameterTypeClass = Object.class;
		}
		String templateStr = TOTAL_COUNT_EXPR_MACRO + " " + SELECT_EXPR_MACRO
				+ " " + script.getNode().getTextContent();

		return new SQLScriptSource(configuration, templateStr,
				parameterTypeClass);
	}

	@Override
	public SqlSource createSqlSource(Configuration configuration,
			String script, Class<?> parameterTypeClass) {
		if (parameterTypeClass == null) {
			parameterTypeClass = Object.class;
		}
		String templateStr = new StringBuilder().append(SELECT_EXPR_MACRO)
				.append(" ").append(TOTAL_COUNT_EXPR_MACRO).append(" ")
				.append(script).toString();
		return new SQLScriptSource(configuration, templateStr,
				parameterTypeClass);
	}

	private static String loadResource(String id) {
		InputStream inputStream = VelocityDriverDeclare.class
				.getResourceAsStream(id);
		if (inputStream == null) {
			throw new MyCollabException("Can not load resource id " + id);
		} else {
			StringBuffer buf = new StringBuffer();

			try {
				int chBug = 0;
				while ((chBug = inputStream.read()) != -1) {
					buf.append((char) chBug);
				}
			} catch (IOException e) {
				throw new MyCollabException(e);
			}
			return buf.toString();
		}
	}

}
