package dev.akbayin.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@NoArgsConstructor
@Getter
@Setter
@ToString
@Entity
@Table(name="task")
public class Task {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name="id")
  private Long id;

  @Column(name="is_done")
  private boolean isDone;

  @Column(name="description")
  private String description;


  public Task(boolean isDone, String description) {
    this.isDone = isDone;
    this.description = description;
  }
}
