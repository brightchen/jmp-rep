package cg.publish.service.report;

import java.io.File;

import org.apache.struts2.ServletActionContext;

import cg.common.logging.Logger;
import cg.publish.web.jasper.ReportDurationUtil;

import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.JRException;
/*
 * This class provides services on jasper template
 */
public class JasperReportTemplateStaticFactory implements JasperReportTemplateFactory
{
    public JasperReport createReportTemplate( ReportInfo reportInfo ) throws JRException
    {
    	reportInfo.setEndDate(ReportDurationUtil.decreaseDate(reportInfo.getEndDate(), 1));
        String templateFile = getTemplateFilePath( reportInfo.getReportType() );
        return JasperCompileManager.compileReport( templateFile );
        //JasperDesign design = JRXmlLoader.load( templateFile );
    }
    
    protected String getDefJasperTemplateDir()
    {
        String defDir = ServletActionContext.getServletContext().getRealPath("/")
                            + "jasper";
        log.info( "default jasper template directory: " + defDir );
        return defDir;
    }
    
    protected String getTemplateFilePath( ReportType reportType )
    {
        return ( ( templateFileDirectory == null ) ? getDefJasperTemplateDir() : templateFileDirectory )
               + File.separator + reportType.getRawTemplateFileName(); 
    }
    
    public String getTemplateFileDirectory()
    {
        return templateFileDirectory;
    }
    public void setTemplateFileDirectory( String templateFileDirectory )
    {
        this.templateFileDirectory = templateFileDirectory;
    }
    
    private static final Logger log = Logger.getLogger( JasperReportTemplateStaticFactory.class );
    private String templateFileDirectory = null;
    
}