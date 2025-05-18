CREATE MATERIALIZED VIEW provider_comment_rate_view as
SELECT
    cm.provider_id provider_id,
    avg(cm.rate) comment_rate,
    count(*) comment_count
from comment cm
WHERE cm.provider_id is not null
  AND cm.status_id = 2
group by cm.provider_id;

CREATE MATERIALIZED VIEW product_provider_id_comment_rate_view as
SELECT
    cm.product_provider_id product_provider_id,
    avg(cm.rate) comment_rate,
    count(*) comment_count
from comment cm
WHERE cm.product_provider_id is not null
  AND cm.status_id = 2
group by cm.product_provider_id;