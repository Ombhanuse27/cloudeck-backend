package com.iot.dashboard.repository;

import com.iot.dashboard.model.Widget;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface WidgetRepository extends JpaRepository<Widget, Long> {
    List<Widget> findByTemplateId(Long templateId);
}