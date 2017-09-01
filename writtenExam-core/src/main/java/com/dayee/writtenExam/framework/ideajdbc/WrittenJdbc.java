/**
 *
 */

package com.dayee.writtenExam.framework.ideajdbc;

import com.dayee.writtenExam.framework.usercontext.UserContext;
import com.dayee.writtenExam.framework.util.StringUtils;
import com.dayee.writtenExam.model.ICorpable;
import com.ideamoment.ideadata.description.IdDescription;
import com.ideamoment.ideadata.description.PropertyDescriptionDecoration;
import com.ideamoment.ideajdbc.IdeaJdbc;
import com.ideamoment.ideajdbc.action.Command;
import com.ideamoment.ideajdbc.action.Query;
import com.ideamoment.ideajdbc.action.UpdateAction;
import com.ideamoment.ideajdbc.action.UpdateCorpAction;
import com.ideamoment.ideajdbc.configuration.IdeaJdbcConfiguration;
import com.ideamoment.ideajdbc.description.DataItemUpperDecoration;
import com.ideamoment.ideajdbc.description.JdbcEntityDescription;
import com.ideamoment.ideajdbc.description.JdbcEntityDescriptionFactory;
import com.ideamoment.ideajdbc.server.Db;
import com.ideamoment.ideajdbc.server.DbManager;
import com.ideamoment.ideajdbc.transaction.Transaction;
import com.ideamoment.ideajdbc.transaction.TxCallable;
import com.ideamoment.ideajdbc.transaction.TxRunnable;
import com.ideamoment.ideajdbc.transaction.TxStrategy;

/**
 * @author Chinakite
 */
public class WrittenJdbc {
	
	static {
		PropertyDescriptionDecoration dataItemUpper = new DataItemUpperDecoration();
		JdbcEntityDescriptionFactory.getInstance()
				.setupPropertyDecoration(dataItemUpper);
		if (!IdeaJdbcConfiguration.inited) {
			IdeaJdbcConfiguration.initConfig("ideajdbc.properties");
		}
	}
	
	/**
	 * 该方法已过时，请换用find(Class<T> clazz, Object id, String corpCode)
	 * <br>
	 * 根据主键查找一个对象。
	 * <ul>
	 * <li>如果使用了HandlerSocket或Memcached API，则直接使用id做为key去查询</li>
	 * <li>如果没有使用key-value方案，则生成主键查询语句去查询。</li>
	 * </ul>
	 *
	 * @param clazz
	 *            实体类
	 * @param id
	 *            主键
	 * @return 如果存在返回对象，如果不存在返回null。
	 */
	public static <T> T find(Class<T> clazz, Object id) {
		
		String dbName = getCorpDbName();
		return IdeaJdbc.db(dbName).find(clazz, id);
	}
	
	/**
	 * 根据主键查询一个实体对象。
	 *
	 * @param clazz
	 *            实体类
	 * @param id
	 *            主键
	 * @param corpCode
	 *            企业代码
	 * @return 实体对象
	 */
	@SuppressWarnings("unchecked")
	public static <T> T find(Class<T> clazz, Object id, String corpCode) {
		
		JdbcEntityDescription entityDesc = JdbcEntityDescriptionFactory
				.getInstance().getEntityDescription(clazz);
		String idDataItem = entityDesc.getIdDescription().getDataItem();
		
		String sql = "select " + entityDesc.allDataItemString()
				+ " from "
				+ entityDesc.getDataSet()
				+ " where "
				+ idDataItem
				+ " = :id and C_CORPCODE = :corpCode";
		
		return (T) WrittenJdbc.query(sql).setParameter("corpCode", corpCode)
				.setParameter("id", id).uniqueTo(clazz);
		
	}
	
	/**
	 * 在默认数据库上构建一个SQL查询
	 *
	 * @param sql
	 *            SQL语句
	 * @return SQL查询
	 */
	public static Query query(String sql) {
		
		String dbName = getCorpDbName();
		
		return IdeaJdbc.db(dbName).query(sql);
	}
	
	/**
	 * 在默认数据库上构建一个预定义名称的sql查询
	 *
	 * @param entityClass
	 *            实体类
	 * @return 查询对象
	 */
	public static <T> Query<T> query(Class<T> entityClass) {
		
		String dbName = getCorpDbName();
		return IdeaJdbc.db(dbName).query(entityClass);
	}
	
	/**
	 * 在默认数据库上构建一个预定义名称的sql查询
	 *
	 * @param entityClass
	 *            实体类
	 * @param sqlName
	 *            SQL名称
	 * @return 查询对象
	 */
	public static <T> Query<T> query(Class<T> entityClass, String sqlName) {
		
		String dbName = getCorpDbName();
		return IdeaJdbc.db(dbName).query(entityClass, sqlName);
	}
	
	/**
	 * @param sql
	 * @return
	 */
	public static Command sql(String sql) {
		
		String dbName = getCorpDbName();
		return IdeaJdbc.db(dbName).sql(sql);
	}
	
	/**
	 * 保存一个实体对象
	 *
	 * @param entity
	 * @return
	 */
	public static Object save(Object entity) {
		
		String dbName = getCorpDbName();
		return IdeaJdbc.db(dbName).save(entity);
	}

	/**
	 * 保存一个实体对象到默认数据库
	 */
	public static Object saveToDefaultDb(Object entity) {

		return IdeaJdbc.defaultDb().save(entity);
	}
	
	/**
	 * 保存一个实体对象
	 *
	 * @param entity
	 * @return
	 */
	public static Object delete(Object entity) {
		
		String dbName = getCorpDbName();
		return IdeaJdbc.db(dbName).delete(entity);
	}
	
	/**
	 * 该方法已过时，请换用delete(Class<T> clazz, Object id, String corpCode)
	 * <br>
	 * 保存一个实体对象
	 *
	 * @return
	 */
	public static int delete(Class<?> entityClass, Object id) {
		
		String dbName = getCorpDbName();
		return IdeaJdbc.db(dbName).delete(entityClass, id);
	}
	
	public static int delete(Class<?> entityClass, Object id, String corpCode) {
		
		JdbcEntityDescription entityDesc = JdbcEntityDescriptionFactory
				.getInstance().getEntityDescription(entityClass);
		IdDescription idDesc = entityDesc.getIdDescription();
		
		StringBuffer sql = new StringBuffer();
		sql.append("delete from ").append(entityDesc.getDataSet())
				.append(" where ").append(idDesc.getDataItem()).append(" =:id");
		System.out.println(sql);
		int r = WrittenJdbc.sql(sql.toString())
				.setParameter("corpCode", corpCode).setParameter("id", id)
				.execute();
		return r;
	}
	
	/**
	 * 更新实体
	 *
	 * @param entity
	 * @return
	 */
	public static int update(Object entity) {
		
		String dbName = getCorpDbName();
		return IdeaJdbc.db(dbName).update(entity);
	}
	
	/**
	 * 构建更新动作
	 *
	 * @param entityClass
	 *            实体类
	 * @param id
	 *            主键
	 * @return
	 */
	public static UpdateAction<?> update(Class<?> entityClass, Object id) {
		
		String dbName = getCorpDbName();
		return IdeaJdbc.db(dbName).update(entityClass, id);
	}
	
	/**
	 * 更新实体
	 *
	 * @param entity
	 * @return
	 */
	public static int updateCorp(ICorpable entity) {
		
		String dbName = getCorpDbName();
		return IdeaJdbc.db(dbName).updateCorp(entity);
	}
	
	/**
	 * 构建更新动作
	 *
	 * @param entityClass
	 *            实体类
	 * @param id
	 *            主键
	 * @param corpCode
	 * @return
	 */
	public static UpdateCorpAction<?> updateCorp(Class<?> entityClass, Object id,String corpCode) {
		
		String dbName = getCorpDbName();
		return IdeaJdbc.db(dbName).updateCorp(entityClass, id, corpCode);
	}
	
	/**
	 * 根据数据库名称获取数据库实例
	 *
	 * @param dbName
	 *            数据库名称
	 * @return 数据库实例
	 */
	public static Db db(String dbName) {
		
		if(StringUtils.isBlank(dbName)){
			dbName=getCorpDbName();
		}
		
		return DbManager.getInstance().getDb(dbName);
	}
	
	public static Db consoleDb() {
		
		return DbManager.getInstance().getDefaultDb();
	}
	
	/**
	 * 为默认数据库开启一个事务,放在当前线程中
	 *
	 * @return 事务实例
	 */
	public static Transaction beginTransaction() {
		
		String dbName = getCorpDbName();
		return IdeaJdbc.db(dbName).beginTransaction();
	}
	
	/**
	 * 获取当前使用的事务;
	 *
	 * @return 事务实例
	 */
	public static Transaction getCurrentTransaction() {
		
		String dbName = getCorpDbName();
		return IdeaJdbc.db(dbName).getCurrentTransaction();
	}
	
	/**
	 * 提交事务
	 */
	public static void commitTransaction() {
		
		String dbName = getCorpDbName();
		IdeaJdbc.db(dbName).commitTransaction();
	}
	
	/**
	 * 结束默认数据库当前线程的事务
	 */
	public static void endTransaction() {
		
		String dbName = getCorpDbName();
		IdeaJdbc.db(dbName).endTransaction();
	}
	
	/**
	 * 执行一段事务代码块
	 *
	 * @param r
	 */
	public static void tx(TxRunnable r) {
		
		String dbName = getCorpDbName();
		IdeaJdbc.db(dbName).tx(TxStrategy.DEFAULT_TX_STRATEGY, r);
	}
	
	/**
	 * 执行一段事务代码块
	 *
	 * @param r
	 */
	public static void tx(TxStrategy strategy, TxRunnable r) {
		
		String dbName = getCorpDbName();
		IdeaJdbc.db(dbName).tx(strategy, r);
	}
	
	/**
	 * 执行一段事务代码块
	 *
	 */
	public static Object tx(TxCallable c) {
		
		String dbName = getCorpDbName();
		return IdeaJdbc.db(dbName).tx(TxStrategy.DEFAULT_TX_STRATEGY, c);
	}
	
	private static String getCorpDbName() {
		
		String dbName=null;
		if(UserContext.getCurrentContext()==null
				|| UserContext.getCurrentContext().getDbName()==null){
			dbName="scorepaper";
		} else {
			dbName=	UserContext.getCurrentContext().getDbName();
		}
		return dbName;
	}
}
