package com.abhinav.College.Discovery.Platform.Controller;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.LinkedHashMap;
import java.util.Map;

@RestController
public class HomeController {

    @GetMapping("/")
    public Map<String, Object> getApiDocumentation() {
        Map<String, Object> docs = new LinkedHashMap<>();

        docs.put("platform", "College Discovery & Rank Prediction API Backend");
        docs.put("status", "ONLINE / OPERATIONAL");
        docs.put("version", "v1.0.0");


        Map<String, Object> searchEndpoints = new LinkedHashMap<>();
        searchEndpoints.put("Endpoint", "GET /api/colleges");
        searchEndpoints.put("Description", "Returns an optimized, window-based slice of colleges filtering by name, state, and rating thresholds without triggering heavy database count queries.");

        Map<String, String> searchParams = new LinkedHashMap<>();
        searchParams.put("search", "Optional text string to match college names (e.g., 'IIT')");
        searchParams.put("state", "Optional string filter for geographical locations (e.g., 'Telangana')");
        searchParams.put("minRating", "Optional double threshold filtering overall institutional score (e.g., 4.5)");
        searchParams.put("lastId", "Optional cursor point tracking the ID of the last element in the previous page. Crucial for infinite scroll memory optimization.");
        searchParams.put("limit", "Optional integer sizing the dynamic response size window (Defaults to 10)");

        searchEndpoints.put("Query_Parameters", searchParams);
        searchEndpoints.put("Example_Live_Call", "/api/colleges?search=IIT&state=Telangana&minRating=4.0&limit=5");


        Map<String, Object> slugEndpoints = new LinkedHashMap<>();
        slugEndpoints.put("Endpoint", "GET /api/colleges/{slug}");
        slugEndpoints.put("Description", "Fetches comprehensive, un-truncated details for a single institutional profile using a unique web-safe text token.");


        Map<String, String> slugParams = new LinkedHashMap<>();
        slugParams.put("slug", "Required URL path string representing the web-safe hyphenated token of the college (e.g., 'iit-hyderabad')");

        slugEndpoints.put("Path_Variables", slugParams);
        slugEndpoints.put("Example_Live_Call", "/api/colleges/iit-hyderabad");

        Map<String, Object> compareEndpoints = new LinkedHashMap<>();
        compareEndpoints.put("Endpoint", "GET /api/colleges/compare");
        compareEndpoints.put("Description", "Aggregates multi-variable metrics side-by-side for multiple colleges. Employs explicit optimization rules to process matrix calculations cleanly.");


        Map<String, String> compareParams = new LinkedHashMap<>();
        compareParams.put("ids", "Required comma-separated list of primary keys (database IDs) representing target profiles (e.g., 1,2,3).");

        Map<String, Object> validationRules = new LinkedHashMap<>();
        validationRules.put("Minimum_Requirement", "Must provide at least 1 valid ID.");
        validationRules.put("Maximum_Constraint", "Restricted to a maximum of 3 colleges side-by-side to optimize UI/UX layouts and grid scaling.");

        compareEndpoints.put("Query_Parameters", compareParams);
        compareEndpoints.put("Business_Validation_Rules", validationRules);
        compareEndpoints.put("Example_Live_Call", "/api/colleges/compare?ids=12,15,18");


        Map<String, Object> authEndpoints = new LinkedHashMap<>();
        authEndpoints.put("Endpoint_Prefix", "/api/auth");
        authEndpoints.put("Description", "Handles user lifecycle states, credential validation, secure password extraction, and stateless session allocation via JWT tokens.");


        Map<String, Object> registerRoute = new LinkedHashMap<>();
        registerRoute.put("Method", "POST /api/auth/signup");
        registerRoute.put("Expected_Payload", "{ \"username\": \"string\", \"password\": \"string\" }");
        registerRoute.put("Description", "Registers a new user inside the ecosystem. Raw passwords undergo industry-standard BCrypt salting and hashing before database entry.");


        Map<String, Object> loginRoute = new LinkedHashMap<>();
        loginRoute.put("Method", "POST /api/auth/login");
        loginRoute.put("Expected_Payload", "{ \"username\": \"string\", \"password\": \"string\" }");

        Map<String, String> loginResponseFormat = new LinkedHashMap<>();
        loginResponseFormat.put("token", "Stateless cryptographically signed JWT token string.");
        loginResponseFormat.put("user.username", "Authenticated profile user identifier.");
        loginResponseFormat.put("user.id", "Unique user primary key tracking sequence.");
        loginRoute.put("Response_Payload_Structure", loginResponseFormat);

        Map<String, String> runtimeHeaders = new LinkedHashMap<>();
        runtimeHeaders.put("Authorization", "Bearer <your_token_string> (Required header parameter to access downstream protected community endpoints)");
        loginRoute.put("Downstream_Usage_Rules", runtimeHeaders);

        authEndpoints.put("Registration_Flow", registerRoute);
        authEndpoints.put("Authentication_Flow", loginRoute);


        Map<String, Object> predictionEndpoints = new LinkedHashMap<>();
        predictionEndpoints.put("Endpoint", "POST /api/v1/predictor");
        predictionEndpoints.put("Description", "Evaluates student entrance performance metrics against multi-category historical database allocations to match users with highly accurate engineering streams.");

        Map<String, String> expectedBodyFormat = new LinkedHashMap<>();
        expectedBodyFormat.put("exam", "Required enum/string identifier for the entrance evaluation index (e.g., 'JEE_MAINS', 'JEE_ADVANCED').");
        expectedBodyFormat.put("category", "Required reservation/quota grouping category (e.g., 'OPEN', 'OBC', 'SC', 'ST').");
        expectedBodyFormat.put("rank", "Required integer value representing the student's final secured position/merit mark.");

        Map<String, Object> validRules = new LinkedHashMap<>();
        validRules.put("Data_Validation", "Enforces fields validation on the Data Transfer Object (PredictorRequest) via Jakarta @Valid metadata tokens to block corrupted metrics.");

        predictionEndpoints.put("Expected_JSON_Payload", expectedBodyFormat);
        predictionEndpoints.put("Validation_Rules", validRules);
        predictionEndpoints.put("Example_Payload_Body", "{ \"exam\": \"JEE_MAINS\", \"category\": \"OPEN\", \"rank\": 14500 }");


        Map<String, Object> discussionEndpoints = new LinkedHashMap<>();
        discussionEndpoints.put("Endpoint_Prefix", "/api/v1/discussions");
        discussionEndpoints.put("Description", "Provides a stateless, interactive Q&A network enabling authenticated users to post query threads, interact with peers, and browse dynamic community discussion feeds.");


        Map<String, Object> browseRoute = new LinkedHashMap<>();
        browseRoute.put("Method", "GET /api/v1/discussions");
        browseRoute.put("Query_Params", "?page=0 (Defaults to page 0 with an optimized window size of 15 elements)");
        browseRoute.put("Description", "Fetches the global discussion thread timeline sorted dynamically by newest creation timestamps using JPA Slice partitions.");


        Map<String, Object> askRoute = new LinkedHashMap<>();
        askRoute.put("Method", "POST /api/v1/discussions/ask");
        askRoute.put("Expected_Payload", "{ \"title\": \"string\", \"content\": \"string\" }");
        askRoute.put("Security", "Requires valid Authorization Bearer Token. The system automatically extracts security contexts seamlessly from memory using @AuthenticationPrincipal.");


        Map<String, Object> answerRoute = new LinkedHashMap<>();
        answerRoute.put("Method", "POST /api/v1/discussions/{questionId}/answer");
        answerRoute.put("Path_Variables", "{questionId} -> Target question entity primary key tracking sequence (e.g., 42)");
        answerRoute.put("Expected_Payload", "{ \"content\": \"string\" }");
        answerRoute.put("Security", "Requires valid Authorization Bearer Token.");

        discussionEndpoints.put("Browse_Feed_Flow", browseRoute);
        discussionEndpoints.put("Publish_Question_Flow", askRoute);
        discussionEndpoints.put("Submit_Answer_Flow", answerRoute);


        Map<String, Object> savedEndpoints = new LinkedHashMap<>();
        savedEndpoints.put("Endpoint_Prefix", "/api/v1/saved");
        savedEndpoints.put("Description", "Manages user bookmarks, saved profiles, and persistent cross-entity comparison grids. Automatically extracts lightweight database tokens and hydrates them into fully populated profile matrices on the fly.");


        Map<String, Object> saveRoute = new LinkedHashMap<>();
        saveRoute.put("Method", "POST /api/v1/saved");
        saveRoute.put("Query_Params", "?type=COLLEGE|COMPARISON&reference=45 (or '12,14,15')");
        saveRoute.put("Security", "Requires valid Authorization Bearer Token. Context resolved via @AuthenticationPrincipal.");


        Map<String, Object> getRawRoute = new LinkedHashMap<>();
        getRawRoute.put("Method", "GET /api/v1/saved");
        getRawRoute.put("Query_Params", "?type=COLLEGE|COMPARISON");
        getRawRoute.put("Description", "Fetches the flat bookmark tracking wrapper metadata for the current authenticated user session.");


        Map<String, Object> collegesRoute = new LinkedHashMap<>();
        collegesRoute.put("Method", "GET /api/v1/saved/colleges");
        collegesRoute.put("Description", "Batch-resolves all individual bookmarked colleges, parsing string pointers and returning fully populated college card details in a single pass.");


        Map<String, Object> bulkCompareRoute = new LinkedHashMap<>();
        bulkCompareRoute.put("Method", "GET /api/v1/saved/comparisons");
        bulkCompareRoute.put("Description", "Iterates through a collection of saved multi-entity comparison combinations. Every entry splits its internal tracking array string and returns an aggregated nested list of objects [[CollegeA, CollegeB], [CollegeC, CollegeD]].");


        Map<String, Object> singleCompareRoute = new LinkedHashMap<>();
        singleCompareRoute.put("Method", "GET /api/v1/saved/comparison/{savedItemId}");
        singleCompareRoute.put("Path_Variables", "{savedItemId} -> Primary key configuration reference ID");
        singleCompareRoute.put("Security", "Enforces Ownership Guard Checks. Validates that the active token ID matches the target entry owner profile directly.");

        savedEndpoints.put("Persist_Bookmark_Flow", saveRoute);
        savedEndpoints.put("Fetch_Raw_Metadata", getRawRoute);
        savedEndpoints.put("Hydrate_Saved_Colleges_Matrix", collegesRoute);
        savedEndpoints.put("Hydrate_All_Saved_Grid_Matrices", bulkCompareRoute);
        savedEndpoints.put("Recreate_Target_Grid_Matrix", singleCompareRoute);

        docs.put("Core_Discovery_Pagination", searchEndpoints);
        docs.put("SEO_Deep_Linking_Slugs", slugEndpoints);
        docs.put("Comparison_Matrix_Rules", compareEndpoints);
        docs.put("Identity_Authentication_Context", authEndpoints);
        docs.put("Rank_Predictor_Engine", predictionEndpoints);
        docs.put("Community_Discussions_Hub", discussionEndpoints);
        docs.put("Saved_Items_Hydration_Engine", savedEndpoints);

        return docs;

    }
}
