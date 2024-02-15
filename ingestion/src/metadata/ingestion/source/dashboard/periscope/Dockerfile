# Base image from the right version
FROM openmetadata/ingestion:0.13.2

# Let's use the same workdir as the ingestion image
WORKDIR ingestion

# Install our custom connector
# For a PROD image, this could be picking up the package from your private package index
COPY connector connector
COPY setup.py .
RUN pip install --no-deps .
