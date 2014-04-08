package ua.org.tees.yarosh.tais.ui.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import ua.org.tees.yarosh.tais.attendance.AttendanceConfiguration;
import ua.org.tees.yarosh.tais.auth.AuthConfiguration;
import ua.org.tees.yarosh.tais.core.common.CommonConfiguration;
import ua.org.tees.yarosh.tais.core.user.mgmt.UserMgmtConfiguration;
import ua.org.tees.yarosh.tais.homework.HomeworkConfiguration;
import ua.org.tees.yarosh.tais.schedule.ScheduleConfiguration;
import ua.org.tees.yarosh.tais.user.comm.configuration.UserCommConfiguration;

@Configuration
@Import({UserMgmtConfiguration.class,
        UserCommConfiguration.class,
        ScheduleConfiguration.class,
        AttendanceConfiguration.class,
        CommonConfiguration.class,
        HomeworkConfiguration.class,
        AuthConfiguration.class,
        UIConfiguration.class})
public class TaisConfiguration {
}
