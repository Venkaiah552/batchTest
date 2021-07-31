package com.cvs.cdc.reader;

import org.springframework.batch.item.database.Order;
import org.springframework.batch.item.database.support.AbstractSqlPagingQueryProvider;
import org.springframework.batch.item.database.support.SqlPagingQueryUtils;

import java.util.Iterator;
import java.util.Map;

public class TeradataPagingQueryProvider extends AbstractSqlPagingQueryProvider {
    @Override
    public String generateFirstPageQuery(int pageSize) {
        //StringBuffer stringBuffer = new StringBuffer(SqlPagingQueryUtils.generateRowNumSqlQuery(this, false, ""));
       // StringBuffer stringBuffer1 = null;

        //stringBuffer1 = new StringBuffer(SqlPagingQueryUtils.generateRowNumSqlQuery(this, false, "")).delete(15,23).delete(342,351);
        /*String sql = SqlPagingQueryUtils.generateRowNumSqlQuery(this, false, "");
        String sql1 = sql.replace("(SELECT ","");*/
        //return  stringBuffer1.toString();
        String string = SqlPagingQueryUtils.generateRowNumSqlQuery(this, false, "").replace("(SELECT ","");
        String string1 = string.replace(") WHERE","");
        return string1;
    }

    @Override
    public String generateRemainingPagesQuery(int pageSize) {
       // StringBuffer stringBuffer1 = null;
       // stringBuffer1 =   new StringBuffer(SqlPagingQueryUtils.generateRowNumSqlQuery(this, true, "")).delete(15,23).delete(342,351);
       // return SqlPagingQueryUtils.generateRowNumSqlQuery(this, true, "");
        String string = SqlPagingQueryUtils.generateRowNumSqlQuery(this, true, "").replace("(SELECT ","");
        String string1 = string.replace(") WHERE","");
        String string2 = string1.replace("  AND ((EXTR_DT > ?) OR (EXTR_DT = ? AND RXC_IMM_ID > ?) OR (EXTR_DT = ? AND RXC_IMM_ID = ? AND JOB_NM > ?))","");
        return  string2;
        //return stringBuffer1.toString();
    }

    @Override
    public String generateJumpToItemQuery(int itemIndex, int pageSize) {
        int page = itemIndex / pageSize;
        int offset = page * pageSize;
        offset = offset == 0 ? 1 : offset;
        String sortKeySelect = this.getSortKeySelect();
        String string = SqlPagingQueryUtils.generateRowNumSqlQueryWithNesting(this, sortKeySelect, sortKeySelect, false, "TMP_ROW_NUM = " + offset);
        return string;
    }
    private String getSortKeySelect() {
        StringBuilder sql = new StringBuilder();
        String prefix = "";
        Iterator i$ = this.getSortKeys().entrySet().iterator();

        while(i$.hasNext()) {
            Map.Entry<String, Order> sortKey = (Map.Entry)i$.next();
            sql.append(prefix);
            prefix = ", ";
            sql.append((String)sortKey.getKey());
        }

        return sql.toString();
    }

    private String buildRowNumClause(int pageSize) {
        return "";//pageSize + " = " + pageSize
        // return "ROWNUM <= " + pageSize;
    }
}
