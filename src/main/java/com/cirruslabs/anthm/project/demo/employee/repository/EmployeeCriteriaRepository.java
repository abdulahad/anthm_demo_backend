package com.cirruslabs.anthm.project.demo.employee.repository;

import com.cirruslabs.anthm.project.demo.employee.model.Employee;
import com.cirruslabs.anthm.project.demo.employee.model.EmployeePage;
import com.cirruslabs.anthm.project.demo.employee.model.EmployeeSearchCriteria;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

@Repository
public class EmployeeCriteriaRepository {

    private final EntityManager entityManager;
    private final CriteriaBuilder criteriaBuilder;

    public EmployeeCriteriaRepository(EntityManager entityManager) {
        this.entityManager = entityManager;
        this.criteriaBuilder = entityManager.getCriteriaBuilder();
    }

    public Page<Employee> findAllWithFilter(EmployeePage employeePage, EmployeeSearchCriteria employeeSearchCriteria) {
        CriteriaQuery<Employee> employeeCriteriaQuery = criteriaBuilder.createQuery(Employee.class);
        Root<Employee> employeeRoot = employeeCriteriaQuery.from(Employee.class);
        Predicate predicate = getPridicate(employeeSearchCriteria, employeeRoot);
        employeeCriteriaQuery.where(predicate);

        sortOrder(employeePage, employeeCriteriaQuery, employeeRoot);

        TypedQuery<Employee> typedQuery = entityManager.createQuery(employeeCriteriaQuery);
        typedQuery.setFirstResult(employeePage.getPageNumber() * employeePage.getPageSize());
        typedQuery.setMaxResults(employeePage.getPageSize());

        Pageable pageable = getPageable(employeePage);

        long employeesCount = getEmployeesCount(predicate);

        return new PageImpl<>(typedQuery.getResultList(), pageable, employeesCount);
    }

    private long getEmployeesCount(Predicate predicate) {
        CriteriaQuery<Long> countQuery = criteriaBuilder.createQuery(Long.class);
        Root<Employee> countRoot = countQuery.from(Employee.class);
        countQuery.select(criteriaBuilder.count(countRoot)).where(predicate);
        return entityManager.createQuery(countQuery).getSingleResult();
    }

    private Predicate getPridicate(EmployeeSearchCriteria employeeSearchCriteria, Root<Employee> employeeRoot) {
        List<Predicate> predicates = new LinkedList<>();
        if (Objects.nonNull(employeeSearchCriteria.getKeywords())) {
            predicates.add(
                    criteriaBuilder.like(
                            employeeRoot.get("firstName"),
                            "%" + employeeSearchCriteria.getKeywords() + "%"
                    )
            );
            predicates.add(
                    criteriaBuilder.like(
                            employeeRoot.get("lastName"),
                            "%" + employeeSearchCriteria.getKeywords() + "%"
                    )
            );
            predicates.add(
                    criteriaBuilder.like(
                            employeeRoot.get("designation"),
                            "%" + employeeSearchCriteria.getKeywords() + "%"
                    )
            );
            predicates.add(
                    criteriaBuilder.like(
                            employeeRoot.get("email"),
                            "%" + employeeSearchCriteria.getKeywords() + "%"
                    )
            );
            predicates.add(
                    criteriaBuilder.like(
                            employeeRoot.get("phone"),
                            "%" + employeeSearchCriteria.getKeywords() + "%"
                    )
            );
            return criteriaBuilder.or(predicates.toArray(new Predicate[0]));
        }
        if (Objects.nonNull(employeeSearchCriteria.getFirstName())) {
            predicates.add(
                    criteriaBuilder.like(employeeRoot.get("firstName"),
                            "%" + employeeSearchCriteria.getFirstName() + "%")
            );
        }
        if (Objects.nonNull(employeeSearchCriteria.getLastName())) {
            predicates.add(
                    criteriaBuilder.like(employeeRoot.get("lastName"),
                            "%" + employeeSearchCriteria.getLastName() + "%")
            );
        }
        if (Objects.nonNull(employeeSearchCriteria.getDesignation())) {
            predicates.add(
                    criteriaBuilder.like(employeeRoot.get("designation"),
                            "%" + employeeSearchCriteria.getDesignation() + "%")
            );
        }
        return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
    }

    private void sortOrder(EmployeePage employeePage, CriteriaQuery<Employee> employeeCriteriaQuery, Root<Employee> employeeRoot) {
        if (employeePage.getSortDirection().equals(Sort.Direction.ASC)) {
            employeeCriteriaQuery.orderBy(criteriaBuilder.asc(employeeRoot.get(employeePage.getSortBy())));
        } else {
            employeeCriteriaQuery.orderBy(criteriaBuilder.desc(employeeRoot.get(employeePage.getSortBy())));
        }

    }

    private Pageable getPageable(EmployeePage employeePage) {
        Sort sort = Sort.by(employeePage.getSortDirection(), employeePage.getSortBy());
        return PageRequest.of(employeePage.getPageNumber(), employeePage.getPageSize(), sort);
    }
}
