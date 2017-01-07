package in.bhargavrao.stackoverflow.natty.utils;

import in.bhargavrao.stackoverflow.natty.entities.AutoComment;
import in.bhargavrao.stackoverflow.natty.entities.AutoComment.*;
import in.bhargavrao.stackoverflow.natty.entities.AutoCommentType;
import in.bhargavrao.stackoverflow.natty.entities.PostReport;

public class AutoCommentUtils {
	
	/**
	 * Checks the report and recommends a good comment for the post. 
	 * @param report The PostReport to check
	 * @return The comment that could be posted
	 */
	public static AutoComment commentForPostReport(PostReport report) {		
		Boolean isPossibleLinkOnly = false;
        Boolean hasNoCodeblock = false;
        Boolean containsBlacklistedWord = false;
        
        /*for(String filter: report.getCaughtFor()){
            //filters to decide which auto-comment to use
            if (!hasNoCodeblock && filter.equalsIgnoreCase("No Code Block")) hasNoCodeblock = true;
            if (!isPossibleLinkOnly && filter.equalsIgnoreCase("Possible Link Only")) isPossibleLinkOnly = true;
            if (!containsBlacklistedWord && filter.equalsIgnoreCase("Contains Blacklisted Word")) containsBlacklistedWord = true;
        }*/
        
        if (report.getCaughtFor().contains("No Code Block")) hasNoCodeblock = true;
        if (report.getCaughtFor().contains("Possible Link Only")) isPossibleLinkOnly = true;
        if (report.getCaughtFor().contains("Contains Blacklisted Word")) containsBlacklistedWord = true;
                
        //decide, which comment to use
        if (hasNoCodeblock && isPossibleLinkOnly && !containsBlacklistedWord) {
        	//link-only
        	System.out.println("link-only");
        	return new AutoComment(AutoCommentType.LINK_ONLY);
        } else {
        	System.out.println("Some NAA");
        	//check the reputation to provide different insctructions for users that can't comment yet
        	if (report.getPost().getAnswerer().getReputation() < 50) {
        		//not enough rep to comment
        		System.out.println("low rep");
        		return new AutoComment(AutoCommentType.NAA_LOW_REP);
        	} else {
        		System.out.println("high rep");
        		return new AutoComment(AutoCommentType.NAA_HIGH_REP);
        	}
        }
	}
}
