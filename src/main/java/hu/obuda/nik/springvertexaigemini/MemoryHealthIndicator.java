package hu.obuda.nik.springvertexaigemini;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.stereotype.Component;

@Component
public class MemoryHealthIndicator implements HealthIndicator {

    @Value("${user.actuator.memory.free-percent-limit}")
    public void setFreeMemoryPercentLimit(double freeMemoryPercentLimit) {
        this.freeMemoryPercentLimit = freeMemoryPercentLimit;
    }
    double freeMemoryPercentLimit;

    @Override
    public Health health() {

        long freeMemory = Runtime.getRuntime().freeMemory();
        long totalMemory = Runtime.getRuntime().totalMemory();
        double freeMemoryPercent = ((double) freeMemory / (double) totalMemory) * 100;

        if (freeMemoryPercent > freeMemoryPercentLimit) {
            return Health.up()
                    .withDetail("freeMemory", freeMemory + " bytes")
                    .withDetail("totalMemory", totalMemory + " bytes")
                    .withDetail("freeMemoryPercent", String.format("%.2f", freeMemoryPercent) + "%")
                    .build();
        } else {
            return Health.down()
                    .withDetail("freeMemory", freeMemory + " bytes")
                    .withDetail("totalMemory", totalMemory + " bytes")
                    .withDetail("freeMemoryPercent", String.format("%.2f", freeMemoryPercent) + "%")
                    .build();
        }

    }
}