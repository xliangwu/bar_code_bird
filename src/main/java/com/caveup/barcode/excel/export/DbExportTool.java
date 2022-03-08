package com.caveup.barcode.excel.export;

import com.caveup.barcode.helper.ZipFileUtil;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.springframework.util.Assert;

import javax.sql.DataSource;
import java.io.File;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * @author xw80329
 * @Date 2019/11/30
 */
@Slf4j
public class DbExportTool {

    /**
     * 导出数据导excel文件中
     *
     * @param dataSource
     * @param sql
     * @throws Exception
     */
    public static File export(DataSource dataSource, String sql) throws Exception {
        Assert.notNull(dataSource, "dataSource should be non-empty");
        Assert.notNull(dataSource, "sql should be non-empty");
        TableData tableData = queryData(dataSource, sql);
        if (Objects.isNull(tableData)) {
            throw new Exception("data is empty");
        }

        File output = File.createTempFile("export_", ".xlsx");
        ExcelExportTool.exportObjects(tableData.getData(), tableData.getHeader(), output.getAbsolutePath());
        return output;
    }

    /**
     * 导出数据导zip文件
     *
     * @param dataSource
     * @param sql
     * @return
     * @throws Exception
     */
    public static File exportAsZipFile(DataSource dataSource, String sql) throws Exception {
        Assert.notNull(dataSource, "dataSource should be non-empty");
        Assert.notNull(dataSource, "sql should be non-empty");

        File zipOutput = null;
        File output = null;
        try {
            zipOutput = File.createTempFile("export_zip_", ".zip");
            output = export(dataSource, sql);
            ZipFileUtil.addFilesToZip(output, zipOutput);
        } catch (Exception e) {
            log.info(e.getMessage());
        } finally {
            FileUtils.deleteQuietly(output);
        }
        return zipOutput;
    }

    private static TableData queryData(DataSource dataSource, String sql) {
        Connection connection = null;
        Statement statement = null;
        ResultSet rs = null;
        try {
            connection = dataSource.getConnection();
            statement = connection.createStatement();
            rs = statement.executeQuery(sql);
            ResultSetMetaData resultSetMetaData = rs.getMetaData();

            int columnCount = resultSetMetaData.getColumnCount();
            List<String> header = new ArrayList<>(columnCount);
            List<List<Object>> data = new ArrayList<>(1024);

            for (int i = 1; i <= columnCount; i++) {
                header.add(resultSetMetaData.getColumnName(i));
            }
            while (rs.next()) {
                List<Object> objects = new ArrayList<>();
                for (int i = 1; i <= columnCount; i++) {
                    Object obj = rs.getObject(i);
                    objects.add(obj);
                }
                data.add(objects);
            }
            log.info("Column information:{}", header);
            log.info("data size:{}", data.size());
            TableData tableData = new TableData();
            tableData.setData(data);
            tableData.setHeader(header);
            return tableData;
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        } finally {
            if (null != rs) {
                try {
                    rs.close();
                } catch (SQLException e) {
                }
            }

            if (null != statement) {
                try {
                    statement.close();
                } catch (SQLException e) {
                }
            }


            if (null != connection) {
                try {
                    connection.close();
                } catch (SQLException e) {
                }
            }
        }
        return null;
    }

    @Data
    private static class TableData {
        private List<String> header;
        private List<List<Object>> data;
    }
}
