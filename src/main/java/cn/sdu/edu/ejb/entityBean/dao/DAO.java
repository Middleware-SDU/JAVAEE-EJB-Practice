package cn.sdu.edu.ejb.entityBean.dao;

/**
 * 
 * @author Yonggang Yuan
 *
 * @param <T>
 */

public interface DAO<T> {

    /**
     * 保存一个实体
     * @param entity
     */
    public void save(T entity);

    /**
     * 更新一个实体
     * @param entity
     * @return 
     */
    public T update(T entity);

    /**
     * 删除一个实体
     * @param entityid 实体ID
     */
    public void delete(Object entityid);

    /**
     * 根据实体的ID获得实体
     * @param <T>
     * @param entityId实体id
     * @return
     */
    public T find(Object entityId);

}
