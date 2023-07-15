package com.project.plan.domain.plan;

import com.project.plan.domain.Category;
import com.project.plan.domain.Member;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
public class Plan {

    @Id
    @GeneratedValue
    @Column(name = "plan_id")
    private Long id;

    @Column(name = "plan_detail")
    private String detail;

    @Enumerated(EnumType.STRING)
    private PlanStatus planStatus;

    @Enumerated(EnumType.STRING)
    private DevelopStatus developStatus;

    private LocalDateTime createdDate;

    private LocalDateTime updatedDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "created_user")
    private Member createdUser;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "updated_user")
    private Member updatedUser;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    private Category category;

    @OneToMany(mappedBy = "plan")
    private List<PlanMember> planMembers = new ArrayList<>();
}
