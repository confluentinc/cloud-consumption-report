SELECT en.ID AS ENV_ID, en.NAME AS ENV_NAME, en.CREATED_DATE as ENV_CREATE_DATE, cl.ID AS CL_ID, cl.NAME AS CL_NAME,
 cl.kind AS CL_KIND,cl.cku_count AS CL_CKU, cl.REGION CL_REGION, cl.AVAILABILITY as CL_AVAILABILITY, cl.CREATED_DATE CL_CREATE_DATE,
 cl.CLOUD CL_CLOUD, cm.METRICS_NAME, cm.METRICS_VALUE, cm.TIMESTAMP AS METRICS_TIMESTAMP, ml.METRICS_LIMIT
FROM environment en INNER JOIN cluster cl ON en.id = cl.environment_id
INNER JOIN cluster_metrics cm ON cm.cluster_id = cl.id
INNER JOIN metrics_limit ml ON ml.cku_kind = COALESCE(CAST(cl.cku_count AS VARCHAR(10)), cl.kind) AND ml.metrics_name = cm.metrics_name
ORDER BY en.NAME, cl.NAME, cm.METRICS_NAME, cm.TIMESTAMP;