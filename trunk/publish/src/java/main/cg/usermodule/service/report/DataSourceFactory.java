package cg.publish.service.report;

import java.util.List;
import net.sf.jasperreports.engine.JRDataSource;


public interface DataSourceFactory
{
    public List<?> createListDataSource( ReportInfo info );
    
    public JRDataSource createJRDataSource( ReportInfo info );
}
