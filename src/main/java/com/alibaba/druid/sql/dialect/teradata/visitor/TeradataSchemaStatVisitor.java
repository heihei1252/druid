/*
 * Copyright 1999-2101 Alibaba Group Holding Ltd.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.alibaba.druid.sql.dialect.teradata.visitor;

import java.util.Map;

import com.alibaba.druid.sql.ast.statement.SQLSelectQuery;
import com.alibaba.druid.sql.ast.statement.SQLSubqueryTableSource;
import com.alibaba.druid.sql.dialect.teradata.ast.expr.TeradataAnalytic;
import com.alibaba.druid.sql.dialect.teradata.ast.expr.TeradataAnalyticWindowing;
import com.alibaba.druid.sql.dialect.teradata.ast.expr.TeradataIntervalExpr;
import com.alibaba.druid.sql.visitor.SchemaStatVisitor;
import com.alibaba.druid.util.JdbcUtils;

public class TeradataSchemaStatVisitor extends SchemaStatVisitor implements TeradataASTVisitor {

    @Override
    public String getDbType() {
        return JdbcUtils.TERADATA;
    }

	@Override
	public boolean visit(TeradataAnalyticWindowing x) {
		return true;
	}

	@Override
	public void endVisit(TeradataAnalyticWindowing x) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean visit(TeradataAnalytic x) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean visit(TeradataIntervalExpr x) {
		return true;
	}

	@Override
	public void endVisit(TeradataIntervalExpr x) {

	}
	
	@Override
	public boolean visit(SQLSubqueryTableSource x) {
		accept(x.getSelect());
		
		String table = (String) x.getSelect().getAttribute(ATTR_TABLE);
		if (aliasMap != null && x.getAlias() != null) {
			if (table != null) {
				this.aliasMap.put(x.getAlias(), table);
			}
			addSubQuery(x.getAlias(), x.getSelect());
			this.setCurrentTable(x.getAlias());
		}
		
		if (table != null) {
			x.putAttribute(ATTR_TABLE, table);
		}
		
		return false;
	}
	
	@Override
	public void endVisit(SQLSubqueryTableSource x) {
		
	}
	

}
