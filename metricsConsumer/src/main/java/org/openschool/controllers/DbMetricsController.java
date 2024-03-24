package org.openschool.controllers;

import org.openschool.entity.DbMetrics;
import org.openschool.services.DbMetricsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class DbMetricsController {

    private final DbMetricsService dbMetricsService;

    @Autowired
    public DbMetricsController(DbMetricsService dbMetricsService) {
        this.dbMetricsService = dbMetricsService;
    }

    @GetMapping("/metrics")
    public String getAllDbMetrics(
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "100") int size,
            Model model) {
        Page<DbMetrics> metricsPage = dbMetricsService.getAllDbMetrics(page, size);
        model.addAttribute("metricsPage", metricsPage);
        return "metrics";
    }

    @GetMapping("/metrics/search")
    public String getDbMetricsById(@RequestParam("id") Long id, Model model) {
        DbMetrics dbMetrics = dbMetricsService.getDbMetricsById(id);
        model.addAttribute("metric", dbMetrics);
        return "metric-details";
    }
}