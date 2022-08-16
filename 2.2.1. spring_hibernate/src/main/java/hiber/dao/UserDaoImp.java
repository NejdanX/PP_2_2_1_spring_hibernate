package hiber.dao;

import hiber.model.Car;
import hiber.model.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.TypedQuery;
import java.util.ArrayList;
import java.util.List;

@Repository
public class UserDaoImp implements UserDao {

   @Autowired
   private SessionFactory sessionFactory;

   @Override
   public void add(User user) {
      sessionFactory.getCurrentSession().save(user);
   }

   @Override
   @SuppressWarnings("unchecked")
   public List<User> listUsers() {
      TypedQuery<User> query=sessionFactory.getCurrentSession().createQuery("from User");
      return query.getResultList();
   }

   @Override
   @SuppressWarnings("unchecked")
   public List<User> getUserByCar(String model, int series) {
      String hql = "FROM User where car.model = :model AND car.series = :series";
      try(Session session = sessionFactory.openSession()) {
         session.beginTransaction();
         Query query = session.createQuery(hql);
         query.setParameter("model", model);
         query.setParameter("series", series);
         return query.getResultList();
      } catch (Exception e) {
         System.out.println("Произошла ошибка, пользователь по такой модели не найден");
         return new ArrayList<>();
      }

   }
}
