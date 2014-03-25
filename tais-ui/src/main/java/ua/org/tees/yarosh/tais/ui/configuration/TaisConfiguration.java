package ua.org.tees.yarosh.tais.ui.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import ua.org.tees.yarosh.tais.attendance.configuration.AttendanceConfiguration;
import ua.org.tees.yarosh.tais.core.common.CommonConfiguration;
import ua.org.tees.yarosh.tais.core.user.mgmt.UserMgmtConfiguration;
import ua.org.tees.yarosh.tais.user.comm.configuration.UserCommConfiguration;

@Configuration
@Import({UserMgmtConfiguration.class, UserCommConfiguration.class,
        AttendanceConfiguration.class, CommonConfiguration.class, UIConfiguration.class})
public class TaisConfiguration {
}
