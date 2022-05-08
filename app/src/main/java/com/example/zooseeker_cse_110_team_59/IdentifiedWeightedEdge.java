package com.example.zooseeker_cse_110_team_59;

import org.jgrapht.alg.util.Pair;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.nio.Attribute;

/**
 * Class:           IdentifiedWeightedEdge
 * Description:     A custom edge class that inherits the <code>DefaultWeightedEdge</code>
 *                  class to use the existing methods provided by the <code>jGraph</code>
 *                  library, but is added with custom fields about the edges
 *                  in our zoo graph
 *
 * Public functions:
 *
 * toString         - overrides the default object toString method to
 *                    customize the output
 * attributeConsumer    - Given a edge and vertex tuple, assigns the id of the edge
 *                        to the value of the attribute
 */
public class IdentifiedWeightedEdge extends DefaultWeightedEdge {
    private String id = null;

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    
    @Override
    public String toString() {
        return "(" + getSource() + " :" + id + ": " + getTarget() + ")";
    }

    public static void attributeConsumer(Pair<IdentifiedWeightedEdge, String> pair, Attribute attr) {
        IdentifiedWeightedEdge edge = pair.getFirst();
        String attrName = pair.getSecond();
        String attrValue = attr.getValue();
        
        if (attrName.equals("id")) {
            edge.setId(attrValue);
        }
    }
}
