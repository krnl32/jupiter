function onInit()
	print("Initialized Test Script")

	if entity:hasComponent("RigidBody2DComponent") then
		print("Entity Has RigidBody2D")
	end
end

function onUpdate(dt)
	--print("Running Test Script: ".. dt)
	--local rb = entity:getComponent("RigidBody2DComponent")
	--rb.mass = rb.mass - dt
	--entity:setComponent("RigidBody2DComponent", rb)
end

function onDestroy()
	print("Destroying Test Script")
end
