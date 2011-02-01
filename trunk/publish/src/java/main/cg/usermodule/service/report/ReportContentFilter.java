
package cg.publish.service.report;

/*
 * the interface provides methods to filter the content
 */
public interface ReportContentFilter
{
    public byte[] filterContent( ReportInfo reportInfo, byte[] orgContent );
}