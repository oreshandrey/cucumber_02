package utilities;

import java.sql.*;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class DBUtility {

    private static Connection connection;
    private static Statement statement;
    private static ResultSet resultSet;

    public static void createConnection(){
        String url = PropertyReader.readProperty("db_url");
        String user = PropertyReader.readProperty("db_username");
        String password = PropertyReader.readProperty("db_password");
        try{
            connection = DriverManager.getConnection(url,user,password);
            if(connection != null){
                System.out.println("Database connection established successfully");
            }else{
                System.out.println("Database connection failed");
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    public static void closeConnection(){
        try {
            if (resultSet != null) {
                resultSet.close();
            }
            if(statement != null){
                statement.close();
            }
            if(connection != null){
                connection.close();
            }
            System.out.println("Database closed successfully");
        } catch(SQLException e){
            e.printStackTrace();
        }
    }

    private static void executeQuery(String query){
        try{
            statement = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
        } catch (SQLException e){
            e.printStackTrace();
        }
        try{
            resultSet= statement.executeQuery(query);
        } catch (SQLException e){
            e.printStackTrace();
        }
    }

    public static int updateQuery(String query) throws SQLException {
        try{
            statement = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
        } catch (SQLException e){
            e.printStackTrace();
        }
        int result = statement.executeUpdate(query);
        if (result == 0){
            throw new RuntimeException("Update was unsuccessful");
        }
        return result;
    }

    public static List<List<Object>> getQueryResultAsListOfLists(String query){
        executeQuery(query);
        List<List<Object>> rowList = new ArrayList<>();
        ResultSetMetaData rsmd;
        try{
            rsmd = resultSet.getMetaData();
            while(resultSet.next()){
                List<Object> row = new ArrayList<>();
                for (int i = 1; i <= rsmd.getColumnCount(); i++){
                    row.add(resultSet.getObject(i));
                }
                rowList.add(row);
            }
        } catch(SQLException e ){
            e.printStackTrace();
        }
        return rowList;
    }

    public static List<Map<String, Object>> getQueryResultListOfMaps(String query){
        executeQuery(query);
        List<Map<String, Object>> rowList = new ArrayList<>();
        ResultSetMetaData rsmd;
        try{
            rsmd = resultSet.getMetaData();
            while(resultSet.next()){
                Map<String, Object> colNameValueMap = new LinkedHashMap<>();
                for (int i = 1; i <= rsmd.getColumnCount(); i++){
                    colNameValueMap.put(rsmd.getColumnName(i), resultSet.getObject(i));
                }
                rowList.add(colNameValueMap);
            }
        } catch(SQLException e){
            e.printStackTrace();
        }
        return rowList;
    }

    public static List<String> getColumnNames(String query){
        executeQuery(query);
        List<String> columns = new ArrayList<>();
        ResultSetMetaData rsmd;
        try{
            rsmd = resultSet.getMetaData();
            int columnCount = rsmd.getColumnCount();
            for(int i = 1; i <= columnCount; i++){
                columns.add(rsmd.getColumnName(i));
            }
        } catch (SQLException e){
            e.printStackTrace();
        }
        return columns;
    }

    public static int getRowCount(){
        int rowCount = 0;
        try{
            resultSet.last();
            rowCount = resultSet.getRow();
        } catch (SQLException e){
            e.printStackTrace();
        }
        return rowCount;
    }




}
