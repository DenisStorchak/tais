package ua.org.tees.yarosh.tais.attendance.api;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ua.org.tees.yarosh.tais.attendance.fprint.FprintScanner;

/**
 * @author Timur Yarosh
 *         Date: 19.03.14
 *         Time: 23:59
 */
@Repository
public interface FPrintScannerRepository extends JpaRepository<FprintScanner, String> {
}
