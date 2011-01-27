/**
 * @author brightc
 * @version 1.0
 * @created 27-Mar-2008
 */

package cg.publish.model;


import javax.persistence.Entity;
import javax.persistence.EntityResult;
import javax.persistence.FieldResult;
import javax.persistence.Id;
import javax.persistence.SqlResultSetMapping;

@SqlResultSetMapping
(
    name="TransactionSummary", 
    entities=
    { 
        @EntityResult( entityClass=TransactionSummary.class, 
                       fields= 
                       {
                            @FieldResult(name="groupId", column="group_id"),
                            @FieldResult(name="impressionNum", column="impression_num")
                            
                       }
        )
    }
)
@Entity
public class TransactionSummary 
{
    @Id
    private String  groupId;
    private Integer impressionNum;

    public TransactionSummary()
    {
    }
    public TransactionSummary( String groupId, int impressionNum )
    {
        setGroupId( groupId );
        setImpressionNum( impressionNum );
    }

    public String toString()
    {
        return  "groupId = " + groupId
                + "; impressionNum = " + impressionNum;
    }
    
    public String getId() 
    {
        return groupId;
    }
    
    //groupId
    public String getGroupId() 
    {
        return groupId;
    }
    public void setGroupId( String groupId ) 
    {
        this.groupId = groupId;
    }

    //impressionNum
    public int getImpressionNum()
    {
        return impressionNum;
    }
    public void setImpressionNum( int impressionNum )
    {
        this.impressionNum = impressionNum;
    }
}