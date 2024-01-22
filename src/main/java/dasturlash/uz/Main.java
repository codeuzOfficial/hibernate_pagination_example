package dasturlash.uz;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.query.Query;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        saveData();
//        pagination();
        paginationWithNativeQuery();
    }

    public static void pagination() {
        StandardServiceRegistry ssr = new StandardServiceRegistryBuilder().configure("hibernate.cfg.xml").build();
        Metadata meta = new MetadataSources(ssr).getMetadataBuilder().build();

        SessionFactory factory = meta.getSessionFactoryBuilder().build();
        Session session = factory.openSession();

        Query<StudentEntity> query = session.createQuery("From StudentEntity s order by s.createdDate", StudentEntity.class);
        query.setMaxResults(30);
        query.setFirstResult(50);
        List<StudentEntity> list = query.list();

        for (StudentEntity student : list) {
            System.out.println(student.getId() + " " + student.getName() + " " + student.getSurname() + " " + student.getCreatedDate());
        }

        factory.close();
        session.close();
    }

    public static void paginationWithNativeQuery() {
        StandardServiceRegistry ssr = new StandardServiceRegistryBuilder().configure("hibernate.cfg.xml").build();
        Metadata meta = new MetadataSources(ssr).getMetadataBuilder().build();

        SessionFactory factory = meta.getSessionFactoryBuilder().build();
        Session session = factory.openSession();

        Query<StudentEntity> query = session.createNativeQuery("SELECT * From student s order by s.created_date limit :limitNum offset :offsetNum ", StudentEntity.class);
        query.setParameter("limitNum", 30);
        query.setParameter("offsetNum", 50);
        List<StudentEntity> list = query.list();

        for (StudentEntity student : list) {
            System.out.println(student.getId() + " " + student.getName() + " " + student.getSurname() + " " + student.getCreatedDate());
        }

        factory.close();
        session.close();
    }

    public static void saveData() {
        StandardServiceRegistry ssr = new StandardServiceRegistryBuilder().configure("hibernate.cfg.xml").build();
        Metadata meta = new MetadataSources(ssr).getMetadataBuilder().build();

        SessionFactory factory = meta.getSessionFactoryBuilder().build();
        Session session = factory.openSession();
        Transaction t = session.beginTransaction();

        // students
        StudentEntity student1 = new StudentEntity();
        student1.setName("Ali");
        student1.setSurname("Aliyev");
        student1.setCreatedDate(LocalDateTime.now());
        session.save(student1);

        StudentEntity student2 = new StudentEntity();
        student2.setName("Valish");
        student2.setSurname("Valiyev");
        student2.setCreatedDate(LocalDateTime.now());
        session.save(student2);

        StudentEntity student3 = new StudentEntity();
        student3.setName("Toshmat");
        student3.setSurname("Toshmatov");
        student3.setCreatedDate(LocalDateTime.now());
        session.save(student3);

        StudentEntity student4 = new StudentEntity();
        student4.setName("Eshmat");
        student4.setSurname("Eshmat");
        student4.setCreatedDate(LocalDateTime.now());
        session.save(student4);

        t.commit();

        factory.close();
        session.close();
    }
}