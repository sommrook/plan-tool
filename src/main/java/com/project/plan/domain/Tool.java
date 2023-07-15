package com.project.plan.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Setter @Getter
public class Tool {

    @Id
    @GeneratedValue
    @Column(name = "tool_id")
    private Long id;

    @Column(name = "tool_name")
    private String name;

    @Column(name = "tool_detail")
    private String detail;

    // JoinColumn => solution 객체를 Tool 이라는 테이블의 어떤 컬럼명으로 정의할 것인지 (같은 테이블을 참조하는 여러개의 컬럼이 있다고 생각해보자)
    // 그렇다면 Tool 엔티티는 Solution 의 어떠한 컬럼과 연관관계를 맺고 있다고 알 수 있지?
    // referencedColumnName 속성 => default 는 대상 테이블의 pk 값으로 지정된다.
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "solution_id")
    private Solution solution;

    // mappedBy의 기준은 나 자신 클래스
    // 즉, 나를 가리키는 컬럼이 무엇인지 명시하는것
    @OneToMany(mappedBy = "tool", cascade = CascadeType.ALL)
    private List<Project> projects = new ArrayList<>();

    private LocalDateTime createdDate;

    private LocalDateTime updatedDate;

    private Boolean isDelete;

}
